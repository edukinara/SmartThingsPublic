/**
 *  Garage Switch
 *
 *  Author: Edwin Kinara
 *  Date: 2014-03-29
 */
 // for the UI
metadata {
	definition (name: "Garage Dooor", author: "Edwin Kinara") {
		capability "Garage Door Control"
        capability "Door Control"
	}

	// simulator metadata
	simulator {
	}

	// UI tile definitions
	tiles {
		standardTile("main", "device.door", width: 2, height: 2, canChangeIcon: true) {
			state "open", label: 'Garage', action: "door control.open", icon: "st.doors.garage.garage-open", backgroundColor: "#79b821"
			state "close", label: 'Garage', action: "door control.close", icon: "st.doors.garage.garage-closed", backgroundColor: "#ffffff"
		}
		main "main"
		details "main"
	}
}

def parse(String description) {
	log.error "This device does not support incoming events"
	return null
}

def open() {
	push('open');
	push('close');
}

def close() {
	push('open');
	push('close');
}

def push(String vals) {
	sendEvent(name: "door", value: vals, isStateChange: true, display: false)
	def successClosure = { response ->
		log.debug "Request was successful, $response"
	}
    
	if (vals == "open") {
    	httpGet("http://garage.jevenet.com:8086/?x10command=DEVICE~sendplc~%22${device.deviceNetworkId}%20on%22", successClosure)
        log.debug "Val: on"
    }
    else {
    	httpGet("http://garage.jevenet.com:8086/?x10command=DEVICE~sendplc~%22${device.deviceNetworkId}%20off%22", successClosure)
        log.debug "Val: off"
    }
}