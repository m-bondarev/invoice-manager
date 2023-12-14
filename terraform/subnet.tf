resource "oci_core_subnet" "public_subnet" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.vcn.id
  cidr_block     = var.public_subnet_cidr_block
  display_name   = "manager-public-subnet"

  route_table_id    = oci_core_route_table.inet_gw_route_table.id
  dhcp_options_id   = oci_core_dhcp_options.dhcp.id
  security_list_ids = [oci_core_security_list.allow_all_secur_list.id]
}

resource "oci_core_subnet" "private_subnet" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.vcn.id
  cidr_block     = var.private_subnet_cidr_block
  display_name   = "manager-private-subnet"

  prohibit_public_ip_on_vnic = true

  route_table_id    = oci_core_route_table.inet_gw_route_table.id
  dhcp_options_id   = oci_core_dhcp_options.dhcp.id
  security_list_ids = [oci_core_security_list.allow_all_secur_list.id]
}

resource "oci_core_security_list" "allow_all_secur_list" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.vcn.id
  display_name   = "manager-allow-all-secur-list"
  egress_security_rules {
    destination      = "0.0.0.0/0"
    destination_type = "CIDR_BLOCK"
    protocol         = "all"
    stateless        = false
  }
  ingress_security_rules {
    protocol    = "6"
    source      = "0.0.0.0/0"
    source_type = "CIDR_BLOCK"
    stateless   = false
    tcp_options {
      max = "22"
      min = "22"
    }
  }
  ingress_security_rules {
    icmp_options {
      code = "4"
      type = "3"
    }
    protocol    = "1"
    source      = "0.0.0.0/0"
    source_type = "CIDR_BLOCK"
    stateless   = false
  }
  ingress_security_rules {
    icmp_options {
      type = "3"
    }
    protocol    = "1"
    source      = element(var.vcn_subnet_cidr_blocks, 0)
    source_type = "CIDR_BLOCK"
    stateless   = false
  }
  ingress_security_rules {
    description = "Function access"
    protocol    = "6"
    source      = "0.0.0.0/0"
    source_type = "CIDR_BLOCK"
    stateless   = false
  }
}