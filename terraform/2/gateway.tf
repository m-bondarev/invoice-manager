#internet
resource "oci_core_internet_gateway" "inet_gw" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.vcn.id
  display_name   = "manager-inet-gw"
}

resource "oci_core_route_table" "inet_gw_route_table" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.vcn.id
  display_name   = "manager-inet-gw-route-table"

  route_rules {
    destination       = "0.0.0.0/0"
    destination_type  = "CIDR_BLOCK"
    network_entity_id = oci_core_internet_gateway.inet_gw.id
  }
}

resource "oci_core_default_route_table" "default_route_table" {
  compartment_id             = var.compartment_ocid
  display_name               = "default-route-table"
  manage_default_resource_id = oci_core_vcn.vcn.default_route_table_id
  route_rules {
    destination       = "0.0.0.0/0"
    destination_type  = "CIDR_BLOCK"
    network_entity_id = oci_core_internet_gateway.inet_gw.id
  }
}

#nat
resource "oci_core_nat_gateway" "nat_gw" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.vcn.id
  display_name   = "manager-nat-gw"
}

resource "oci_core_route_table" "nat_gw_route_table" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.vcn.id
  display_name   = "manager-nat-gw-route-table"

  route_rules {
    destination       = "0.0.0.0/0"
    destination_type  = "CIDR_BLOCK"
    network_entity_id = oci_core_nat_gateway.nat_gw.id
  }
}

##api
resource "oci_apigateway_gateway" "manager_api_gw" {
  compartment_id = var.compartment_ocid
  display_name   = "foo-manager-gw"
  endpoint_type  = "PUBLIC"
  subnet_id      = oci_core_subnet.public_subnet.id
  response_cache_details {
    type = "NONE"
  }
}

resource oci_apigateway_deployment manager_api_gw_deployment {
  compartment_id = var.compartment_ocid
  display_name   = "foo-GW-deployment"
  gateway_id     = oci_apigateway_gateway.manager_api_gw.id
  path_prefix    = "/api"
  specification {
    logging_policies {
      execution_log {
        log_level = "INFO"
      }
    }
    request_policies {
      mutual_tls {
        is_verified_certificate_required = "false"
      }
    }
    routes {
      backend {
        function_id = oci_functions_function.function_01.id
        type        = "ORACLE_FUNCTIONS_BACKEND"
      }
      logging_policies {
        execution_log {
          log_level = "INFO"
        }
      }
      methods = [
        "GET",
      ]
      path = "/test"
    }
  }
}

