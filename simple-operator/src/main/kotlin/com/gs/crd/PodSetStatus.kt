package com.gs.crd

import io.fabric8.kubernetes.api.model.KubernetesResource

class PodSetStatus(
        val availableReplicas: Int
): KubernetesResource {

}
