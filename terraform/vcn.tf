resource "oci_core_vcn" "vcn" {
  compartment_id = var.compartment_ocid
  cidr_blocks    = var.vcn_subnet_cidr_blocks
  is_ipv6enabled = false
  display_name   = "manager-vcn"
}

resource "oci_core_dhcp_options" "dhcp" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.vcn.id
  display_name   = "manager-dhcp-options"

  options {
    type        = "DomainNameServer"
    server_type = "VcnLocalPlusInternet"
  }
  options {
    type                = "SearchDomain"
    search_domain_names = ["amanagervcn.oraclevcn.com"]
  }
}
