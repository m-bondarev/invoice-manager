resource "oci_artifacts_container_repository" "manager_registry" {
  compartment_id = var.compartment_ocid
  display_name   = "manager-registry"
  is_immutable   = false
  is_public      = true
}

resource "oci_artifacts_container_configuration" "container_configuration" {
  compartment_id                      = var.compartment_ocid
  is_repository_created_on_first_push = true
}

resource "oci_container_instances_container_instance" "manager_container_instance" {
  depends_on               = [oci_ons_notification_topic.export_invoice_manager_devops_notification]
  availability_domain      = data.oci_identity_availability_domains.ads.availability_domains[0]["name"]
  compartment_id           = var.compartment_ocid
  container_restart_policy = "ALWAYS"
  containers {
    display_name          = "manager-container-${timestamp()}"
    environment_variables = {
      OCI_CLI_KEY_CONTENT = var.private_key,
      OCI_CLI_REGION      = var.region,
      OCI_CLI_USER        = var.user_ocid,
      OCI_CLI_TENANCY     = var.tenancy_ocid,
      OCI_CLI_FINGERPRINT = var.fingerprint,
      OCI_TOPIC_ID        = oci_ons_notification_topic.export_invoice_manager_devops_notification.id
    }
    image_url                      = "${var.region}.ocir.io/frkogu3mhwjt/invoice-manager:latest"
    is_resource_principal_disabled = false
    security_context {
      is_non_root_user_check_enabled = "false"
      is_root_file_system_readonly   = "false"
      security_context_type          = "LINUX"
    }
  }
  display_name                         = "container-instance-${timestamp()}"
  graceful_shutdown_timeout_in_seconds = 10
  shape                                = "CI.Standard.A1.Flex"
  shape_config {
    memory_in_gbs = "4"
    ocpus         = "4"
  }
  state = "ACTIVE"
  vnics {
    display_name          = "invoice-manager-vcn"
    is_public_ip_assigned = true
    nsg_ids               = [
      oci_core_network_security_group.all_inbound_requests.id,
    ]
    private_ip             = "10.0.2.100"
    skip_source_dest_check = true
    subnet_id              = oci_core_subnet.public_subnet.id
  }
}

data "oci_identity_availability_domains" "ads" {
  compartment_id = var.compartment_ocid
}

resource "oci_core_network_security_group" "all_inbound_requests" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.vcn.id
  display_name   = "manager-backen-secur-group"
}


resource oci_core_network_security_group_security_rule all_inbound_requests_network_security_group_security_rule {
  direction                 = "INGRESS"
  network_security_group_id = oci_core_network_security_group.all_inbound_requests.id
  protocol                  = "all"
  source                    = "0.0.0.0/0"
  source_type               = "CIDR_BLOCK"
  stateless                 = "false"
}

resource oci_core_instance manager-instance {
  agent_config {
    are_all_plugins_disabled = "false"
    is_management_disabled   = "false"
    is_monitoring_disabled   = "false"
    plugins_config {
      desired_state = "DISABLED"
      name          = "Vulnerability Scanning"
    }
    plugins_config {
      desired_state = "DISABLED"
      name          = "Compute RDMA GPU Monitoring"
    }
    plugins_config {
      desired_state = "ENABLED"
      name          = "Compute Instance Monitoring"
    }
    plugins_config {
      desired_state = "DISABLED"
      name          = "Compute HPC RDMA Auto-Configuration"
    }
    plugins_config {
      desired_state = "DISABLED"
      name          = "Compute HPC RDMA Authentication"
    }
    plugins_config {
      desired_state = "DISABLED"
      name          = "Block Volume Management"
    }
    plugins_config {
      desired_state = "DISABLED"
      name          = "Bastion"
    }
  }
  availability_config {
    is_live_migration_preferred = "true"
    recovery_action             = "RESTORE_INSTANCE"
  }
  availability_domain = data.oci_identity_availability_domains.ads.availability_domains[0]["name"]
  compartment_id      = oci_artifacts_container_configuration.container_configuration.id
  create_vnic_details {
    assign_public_ip = true
    display_name     = "manager-instance-vcn"
    nsg_ids          = [
      oci_core_network_security_group.all_inbound_requests.id,
    ]
    private_ip             = "10.0.2.101"
    skip_source_dest_check = false
    subnet_id              = oci_core_subnet.public_subnet.id
  }
  display_name = "oci-ci-cd-instance-01"
  instance_options {
    are_legacy_imds_endpoints_disabled = false
  }
  launch_options {
    boot_volume_type                    = "PARAVIRTUALIZED"
    firmware                            = "UEFI_64"
    is_consistent_volume_naming_enabled = "true"
    is_pv_encryption_in_transit_enabled = "true"
    network_type                        = "PARAVIRTUALIZED"
    remote_data_volume_type             = "PARAVIRTUALIZED"
  }
#  metadata = {
#    "ssh_authorized_keys" = var.ssh_author_key
#  }
  shape = "VM.Standard.A1.Flex"
  shape_config {
    memory_in_gbs             = 4
    ocpus                     = 4
    vcpus                     = 4
  }
  source_details {
    boot_volume_vpus_per_gb = "10"
    source_id               = var.image_id
    source_type             = "image"
  }
  state = "RUNNING"
}
