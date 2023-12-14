output "function_test" {
  value = "${oci_apigateway_deployment.manager_api_gw_deployment.endpoint}${oci_apigateway_deployment.manager_api_gw_deployment.specification[0].routes[0].path}"
}
