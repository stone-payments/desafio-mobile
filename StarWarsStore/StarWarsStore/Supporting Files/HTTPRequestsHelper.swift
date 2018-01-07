//
//  HTTPRequestsHelper.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 27/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import Foundation

/// Performs a GET operation at the specified url.
///
/// - Parameters:
///   - url: Url to be visited.
///   - completion: The completion handler to call when the load request is complete.
public func getHTTP(at url: String, withCompletion completion: @escaping ([[String : Any]]?, String?) -> Void) {
	URLSession.shared.dataTask(with: URL(string: url)!) { (data, response, error) in
		if error != nil {
			print(error!.localizedDescription)
			completion(nil, error!.localizedDescription)
		}
		else {
			
			do {
				if let jsonObj = try JSONSerialization.jsonObject(with: data!, options: [.allowFragments]) as? [[String : Any]] {
					DispatchQueue.main.async {
						completion(jsonObj, nil)
					}
					
				}
				else {
					completion(nil, "Could not parse JSON.")
				}
			}
			catch let error as NSError {
				print(error)
				completion(nil, error.localizedDescription)
			}
		}
	}.resume()
}

/// Performs a POST operation at the specified url.
///
/// - Parameters:
///   - url: Url to be visited.
///   - completion: The completion handler to call when the load request is complete.
public func postHTTP(at url: String, withBody json: [String : Any], withCompletion completion: @escaping (String?) -> Void) {
	var request = URLRequest(url: URL(string: url)!)
	request.httpMethod = "POST"
	request.addValue("application/json", forHTTPHeaderField: "Content-Type")
	
	let jsonData = try? JSONSerialization.data(withJSONObject: json)
	request.httpBody = jsonData
	
	URLSession.shared.dataTask(with: request) { (data, response, error) in
		if error != nil {
			print(error!.localizedDescription)
			completion(error!.localizedDescription)
		}
		else {
			let responseCode = (response as! HTTPURLResponse).statusCode
			
			if responseCode == 201 || responseCode == 200 {
				completion(nil)
			}
			else {
				completion("Error on response (code: \(responseCode)).")
			}
		}
	}.resume()
}
