variable "user_ocid" {}
variable "fingerprint" {}
variable "tenancy_ocid" {}
variable "region" {}
variable "private_key" {}
variable "compartment_ocid" {}

#network variables
variable "vcn_subnet_cidr_blocks" {
  default = ["10.0.0.0/16"]
}
variable "private_subnet_cidr_block" {
  default = "10.0.1.0/24"
}
variable "public_subnet_cidr_block" {
  default = "10.0.2.0/24"
}

# storage variables
variable "inv_manager_bucket" {
  type = map(string)
  default = {
    bucket_name = "invoice_manager_bucket"
    prefix      = "storage/"
  }
}