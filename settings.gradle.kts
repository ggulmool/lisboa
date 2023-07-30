rootProject.name = "lisboa"

include("bootstrap:lisboa-batch")
include("bootstrap:lisboa-web")
include("application")
include("adapters:lisboa-in-web")
include("adapters:lisboa-out-collector")
include("adapters:lisboa-out-persistence")