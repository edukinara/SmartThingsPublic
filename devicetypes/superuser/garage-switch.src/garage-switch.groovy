/**
 *  Garage Switch
 *
 *  Author: Edwin Kinara
 *  Date: 2014-03-29
 */
 // for the UI
metadata {
	definition (name: "Garage Switch", author: "Edwin Kinara") {
		capability "Switch"
	}

	// simulator metadata
	simulator {
	}

	// UI tile definitions
	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: 'Garage', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
			state "on", label: 'Garage', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821"
		}
		main "switch"
		details "switch"
	}
}

def parse(String description) {
	log.error "This device does not support incoming events"
	return null
}

def on() {
	push('on');
	push('off');
}

def off() {
	push('off');
}

def push(String vals) {
	sendEvent(name: "switch", value: vals, isStateChange: true, display: false)
	def successClosure = { response ->
		log.debug "Request was successful, $response"
	}
	httpGet("http://garage.jevenet.com:8086/?x10command=DEVICE~sendplc~%22${device.deviceNetworkId}%20$vals%22", successClosure)
}
