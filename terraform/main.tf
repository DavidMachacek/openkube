provider "azurerm" {
    # The "feature" block is required for AzureRM provider 2.x. 
    # If you are using version 1.x, the "features" block is not allowed.
    version = "~>2.0"
    features {}

  subscription_id = "5d25e043-0a56-4710-9c8e-c7bced4ec3d9"
  tenant_id       = "706d7bcf-0631-463c-93e3-3a81f2701686"
}

terraform {
    backend "azurerm" {}
}