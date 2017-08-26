//
//  ViewController.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 21/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        ServiceManager.shared
            .Get(url: "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/productsz.json",
                        parameters: nil,
                        success: {
                            result in
                            print(result) },
                        failure: {
                            failure in
                            print(failure) })
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

