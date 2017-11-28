//
//  Stone_Store_StarTests.swift
//  Stone Store StarTests
//
//  Created by Kennedy Noia on 27/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import XCTest
import Alamofire
import RealmSwift
@testable import Stone_Store_Star

class Stone_Store_StarTests: XCTestCase {
    
    override func setUp() {
        super.setUp()
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    //    Test GET Request to retrieve the products from json
    func testProductsRequest() {
        let expect = expectation(description: "Get products.json")
        Alamofire.request("https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json").validate().responseJSON { response in
            var success = false
            switch response.result {
            case .success:
                if response.result.value != nil {
                    success = true
                    expect.fulfill()
                }
                XCTAssertTrue(success)
            case .failure(let error):
                print(error)
                XCTAssertTrue(success)
            }
        }
        waitForExpectations(timeout: 5, handler: nil)
    }
    
    //    Test GET Request to retrieve the products from json and simple
    //check if match the quantity of products for synchronization.
    func testIfProductsIsSync() {
        let expect = expectation(description: "Get products.json")
        Alamofire.request("https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json").validate().responseJSON { response in
            switch response.result {
            case .success:
                if let json = response.result.value {
                    
                    let dataHelper = DataHelper()
                    let products = dataHelper.getAllProducts()
                    
                    var jsonProductsQuantity = 0
                    for _ in json as! [Any] {
                        jsonProductsQuantity += 1
                    }
                    XCTAssertEqual(products.count, jsonProductsQuantity)
                    expect.fulfill()
                }
            case .failure(let error):
                print(error)
            }
        }
        waitForExpectations(timeout: 5, handler: nil)
    }
    
    // Test the menagement(add/remove) of products inside a opened order
    func testAddAndRemoveProductInsideOrder() {
        let expect = expectation(description: "Request Products")
        let dataHelper = DataHelper()
        let seed = Seed()
        seed.loadProducts {
            let product = dataHelper.getAllProducts().first
            let openedOrder = dataHelper.getOpenedOrder()
            var found = false
            for productInsideOrder in openedOrder.products {
                if (productInsideOrder.title == product?.title &&
                    productInsideOrder.price == product?.price &&
                    productInsideOrder.seller == product?.seller){
                    found = true
                }
            }
            XCTAssertFalse(found)
            dataHelper.addToOpenedOrder(product: product!)
            
            for productInsideOrder in openedOrder.products {
                if (productInsideOrder.title == product?.title &&
                    productInsideOrder.price == product?.price &&
                    productInsideOrder.seller == product?.seller){
                    found = true
                }
            }
            XCTAssertTrue(found)
            dataHelper.removeFromOpenedOrder(product: product!)
            found = false
            for productInsideOrder in openedOrder.products {
                if (productInsideOrder.title == product?.title &&
                    productInsideOrder.price == product?.price &&
                    productInsideOrder.seller == product?.seller){
                    found = true
                }
            }
            XCTAssertFalse(found)
            expect.fulfill()
        }

        waitForExpectations(timeout: 5, handler: nil)
    }
    
    // Test the POST Request registering the payment transaction
    func testPaymentRequest() {
        let expect = expectation(description: "Post transaction payment")
        
        let totalPrice = 8000
        let cardNumber = "1234123412341234"
        let expDate = Date()
        let cvv = 012
        let holderName = "Luke Skywalker"
        let calendar = Calendar.current
        let month = calendar.component(.month, from: expDate)
        let year = calendar.component(.year, from: expDate)
        let parameters: Parameters = [
            "card_number": cardNumber,
            "value": String(totalPrice),
            "cvv": String(cvv),
            "card_holder_name": holderName,
            "exp_date": "\(month)/\(year)"
        ]
        var success = false
        Alamofire.request("http://private-9c299-stonestorestar.apiary-mock.com/transactions",
                          method: .post,
                          parameters: parameters,
                          encoding: JSONEncoding.default).responseJSON { response in
                            switch response.result {
                            case .success:
                                if response.result.value != nil {
                                    success = true
                                    expect.fulfill()
                                }
                                XCTAssertTrue(success)
                            case .failure(let error):
                                print(error)
                                print("RESPONSE~>")
                                debugPrint(response)
                            }
        }
        
        waitForExpectations(timeout: 5, handler: nil)
    }
    
    // Test the menagement of products inside a opened order
    func testCloseOrder() {
        let expect = expectation(description: "Request Products")
        let dataHelper = DataHelper()
        let seed = Seed()
        seed.loadProducts {
            let product = dataHelper.getAllProducts().first
            var openedOrder = dataHelper.getOpenedOrder()
            let transactions = dataHelper.getAllTransactions()
            
            XCTAssertEqual(openedOrder.products.count, 0)
            
            dataHelper.addToOpenedOrder(product: product!)
            
            XCTAssertEqual(openedOrder.products.count, 1)
            XCTAssertEqual(transactions.count, 0)
            
            dataHelper.closeOrder(completionHander: {
                openedOrder = dataHelper.getOpenedOrder()
                XCTAssertEqual(openedOrder.products.count, 0)
                XCTAssertEqual(transactions.count, 1)
                
                expect.fulfill()
            })
        }
        
        waitForExpectations(timeout: 5, handler: nil)
    }
}
