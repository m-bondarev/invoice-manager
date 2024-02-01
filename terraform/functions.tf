resource oci_functions_application export_database_test {
  compartment_id = var.compartment_ocid
  config         = {
  }
  defined_tags = {
    "Oracle-Tags.CreatedBy" = "default/m.bondarev"
    "Oracle-Tags.CreatedOn" = "2024-01-25T12:18:03.960Z"
  }
  display_name  = "database_test"
  freeform_tags = {
  }
  network_security_group_ids = [
    "ocid1.networksecuritygroup.oc1.eu-frankfurt-1.aaaaaaaaydhzp34fytmudainoozotju5j63e2vczr66bguwsna4uwujjwbta",
  ]
  shape      = "GENERIC_X86"
  subnet_ids = [
    "ocid1.subnet.oc1.eu-frankfurt-1.aaaaaaaa55ywthyh63j23yhb4fc5hvxge4q3mo2y5iw2bxwwijtgcgxydlqq",
    "ocid1.subnet.oc1.eu-frankfurt-1.aaaaaaaaxwowdxybywt4wspcufhfukaaw5pr6ec7ieoub44jey5k6dooiiqa",
  ]
  syslog_url = ""
  trace_config {
    domain_id  = ""
    is_enabled = "false"
  }
}

resource oci_functions_function export_my-func {
  application_id = oci_functions_application.export_database_test.id
  config         = {
  }
  defined_tags = {
    "Oracle-Tags.CreatedBy" = "default/m.bondarev"
    "Oracle-Tags.CreatedOn" = "2024-01-26T10:51:28.748Z"
  }
  display_name  = "my-func"
  freeform_tags = {
  }
  image              = "fra.ocir.io/frkogu3mhwjt/invoice-manager/my-func:0.0.23"
  image_digest       = "sha256:6566772f9a9d2640eb3b85e54a7228276a2d87f488ce55091a95441b69e875c7"
  memory_in_mbs      = "128"
  timeout_in_seconds = "30"
  trace_config {
    is_enabled = "false"
  }
}
