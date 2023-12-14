resource "oci_functions_application" "export_test_function" {
  depends_on     = [oci_core_subnet.public_subnet]
  compartment_id = var.compartment_ocid
  display_name   = "test-function-01"
  shape          = "GENERIC_X86"
  subnet_ids = [
    oci_core_subnet.public_subnet.id,
  ]
  trace_config {
    is_enabled = "false"
  }
}

resource "oci_functions_function" "function_01" {
  depends_on     = [oci_functions_application.export_test_function]
  application_id = oci_functions_application.export_test_function.id
  display_name   = "test-function-01"
  image          = "fra.ocir.io/frkogu3mhwjt/test_fn/test-java:0.0.3"
  image_digest   = "sha256:bdbf7e44f9938bd2eccbd8da576c79f52d0ae0cad9e80d2be98307c58a0bb376"
  memory_in_mbs  = "128"
  provisioned_concurrency_config {
    strategy = "NONE"
  }
  timeout_in_seconds = "30"
  trace_config {
    is_enabled = "false"
  }
}

