//
//  HTTPRequestsHelper.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 27/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import Foundation

/// Performs a HTTP request.
///
/// - Parameters:
///   - request: The request to be done.
///   - completion: The completion handler to call when the load request is complete.
private func performHTTPRequest(_ request: URLRequest, withCompletion completion: @escaping (String, String?) -> Void) {
	URLSession.shared.dataTask(with: request, completionHandler: { (data, response, error) in
		if error != nil {
			completion("", (error!.localizedDescription) as String)
		}
		else {
			completion(String(data: data!, encoding: String.Encoding.utf8)!, nil)
		}
	}).resume()
}

/// Transform a JSON object into a dictionary.
///
/// - Parameter json: JSON object to be transformed.
/// - Returns: Dictionary parsed from the JSON object.
private func transformJSONToDict(_ json: String) -> Dictionary<String, AnyObject> {
	if let data = json.data(using: String.Encoding.utf8) {
		do {
			if let jsonObj = try JSONSerialization.jsonObject(with: data,
			                                                  options: JSONSerialization.ReadingOptions(rawValue: 0)) as? Dictionary<String, AnyObject> {
				return jsonObj
			}
		}
		catch {
			print("Error parsing data")
		}
	}
	
	return [String: AnyObject]()
}

/// Performs a get operation at the specified url.
///
/// - Parameters:
///   - url: Url to be visited.
///   - completion: The completion handler to call when the load request is complete.
public func getHTTP(at url: String, withCompletion completion: @escaping (Dictionary<String, AnyObject>, String?) -> Void) {
	var request = URLRequest(url: URL(string: url)!)
	request.setValue("application/json", forHTTPHeaderField: "Accept")
	
	performHTTPRequest(request) { (data: String, error: String?) -> Void in
		if error != nil {
			completion(Dictionary<String, AnyObject>(), error)
		}
		else {
			completion(transformJSONToDict(data), nil)
		}
	}
}
