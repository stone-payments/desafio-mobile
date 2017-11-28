//
//  Stone_Store_StarUITests.swift
//  Stone Store StarUITests
//
//  Created by Kennedy Noia on 27/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import XCTest
import RealmSwift
@testable import Stone_Store_Star

class Stone_Store_StarUITests: XCTestCase {
        
    override func setUp() {
        super.setUp()
        
        // Put setup code here. This method is called before the invocation of each test method in the class.
        
        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false
        // UI tests must launch the application that they test. Doing this in setup will make sure it happens for each test method.
        XCUIApplication().launch()

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    // Test the UI checking the collection and table view cells quantity
    //to conform the normal flow inside de app
    func testCloseOrderAndCleanCartItemSelections() {
        let app = XCUIApplication()
        
        //  Go to Transaction View Controller
        app.buttons["button transactions"].tap()
        let transactionsBeforeCellsQuery = app.tables.cells
        //  Check if transactions collections is empty
        XCTAssertEqual(transactionsBeforeCellsQuery.count, 0)
        let transactionsBeforeCellsCount = transactionsBeforeCellsQuery.count
        
        //  Back to Products View Controller
        app.buttons["button back"].tap()
        
        //  Add some products to opened order
        let productsCellsQuery = app.collectionViews.cells
        productsCellsQuery.otherElements.containing(.staticText, identifier:"Blusa do Imperio").buttons["       Adicionar"].tap()
        productsCellsQuery.otherElements.containing(.staticText, identifier:"R$29,90").buttons["       Adicionar"].tap()
        productsCellsQuery.otherElements.containing(.staticText, identifier:"Boneco de StormTrooper").buttons["       Adicionar"].tap()
        productsCellsQuery.otherElements.containing(.staticText, identifier:"R$50,00").buttons["       Adicionar"].tap()
        productsCellsQuery.otherElements.containing(.staticText, identifier:"Moletom Vader").buttons["       Adicionar"].tap()
        //  Go to Order View Controller
        app.buttons["button order"].tap()
        
        let orderCellsQuery = app.tables.cells
        //  Check if the amount of items inside the table view is the same as added before
        XCTAssertEqual(orderCellsQuery.count, 5)
        //  Do POST Request payment transaction
        app.buttons["button stone payment"].tap()
        
        //  Wait some time until the request is complete, so can find the alert
        sleep(5)
        app.alerts["Stone Payment"].buttons["OK"]/*@START_MENU_TOKEN@*/.press(forDuration: 0.9);/*[[".tap()",".press(forDuration: 0.9);"],[[[-1,1],[-1,0]]],[0]]@END_MENU_TOKEN@*/
        
        let transactionsAfterCellsQuery = app.tables.cells
        //  Check if table view of transactions now has more one item(the new one added)
        XCTAssertEqual(transactionsAfterCellsQuery.count, transactionsBeforeCellsCount+1)
        //  Back to Products View Controller
        app.buttons["button back"].tap()
        //  Back to Order View Controller
        app.buttons["button order"].tap()
        let emptyOrderCells = app.tables.cells
        //  Check if the new opened order is empty
        XCTAssertEqual(emptyOrderCells.count, 0)
    }
    
}
