import groovy.json.JsonSlurper
import org.apache.jmeter.assertions.AssertionResult

class CustomAssert {

	def getRoot(prev) {
        def jsonSlurper = new JsonSlurper()
        return jsonSlurper.parseText(prev.getResponseDataAsString())		
	}

    def parseResponse(json) {
        def jsonSlurper = new JsonSlurper()
        return jsonSlurper.parseText(json)
    }
 
	def check200(prev) {   
    	// this is the basic is it running test
		if (!prev.getResponseCode().equals("200")) {
			AssertionResult.setFailureMessage("Expected response code [200] but got ["+prev.getResponseCode()+"]");
			AssertionResult.setFailure(true);
		}
	}

	def checkShape(root, failureMessage) {	
		// for root parent node, use helper to check expected children; for all others
		// it's brute force
		if (!root.keySet().containsAll(["result"])) {
			failureMessage += "root shape has changed ["+jsonResponse.keySet()+"]";
		}
		return failureMessage;
	}

    def assertResult(assertionResult, expectedResult, actualResult) {
        if (!expectedResult.equals(actualResult)) {
            assertionResult.setFailure(true);
            assertionResult.setFailureMessage("Expected ${expectedResult} but was ${actualResult}");
        }
    }

	def processFailureMessage(failureMessage) {
		if (failureMessage?.trim()) {
			AssertionResult.setFailureMessage(failureMessage);
			AssertionResult.setFailure(true);
		}	
	}

	def checkExpectedValue(value, expectedValue, msg) {
		msg += (value.equals(expectedValue) ? "" : "found ["+value+"] expected ["+expectedValue+"]");
		return msg;
	}
	
	def simpleFunction() {
		System.out.println("+++ in simpleFunction()");
	}
	
	def checkMandatoryFields(map, msg) {
		map.each{
			System.out.println("checking ["+it.key+"] ["+it.value+"]");
			System.out.println("check ");
			if (it.value == null) {
				msg += "+++ shape change: missing mandatory property ["+it.key+"]";
			} else if (it.value == "" ) {
				msg += "+++ shape change: mandatory property ["+it.key+"] has missing value ["+it.value+"]";
			} else {
				//return "";
			}
		}
		return msg;
	}

}

vars.putObject('customAssert', new CustomAssert())