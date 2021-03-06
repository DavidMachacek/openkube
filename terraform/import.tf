resource "azurerm_resource_group" "k8s" {
  name     = var.resource_group_name
  location = var.location
  }

resource "azurerm_log_analytics_workspace" "test" {
    # The WorkSpace name has to be unique across the whole of azure, not just the current subscription/tenant.
    name                = "${var.log_analytics_workspace_name}" #-${random_id.log_analytics_workspace_name_suffix.dec}
    location            = var.log_analytics_workspace_location
    resource_group_name = azurerm_resource_group.k8s.name
    sku                   = var.log_analytics_workspace_sku
    tags                 = {
          Owner = var.owner
        }
}