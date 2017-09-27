//
//  StoreViewController.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 26/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import UIKit
import AlamofireImage

class StoreViewController: UIViewController {
  
  @IBOutlet weak var productCollectionView: UICollectionView!
  @IBOutlet weak var cartButton: UIButton!
  
  private var productViewModel: [ProductViewModel]?
  
  override func viewDidLoad() {
    super.viewDidLoad()
    UIApplication.shared.statusBarStyle = .default
    
    self.productCollectionView.layer.shadowColor = UIColor(red: 0.6, green: 0.6, blue: 0.6, alpha: 1.0).cgColor

  }
  
  override func viewWillAppear(_ animated: Bool) {
    self.refreshProductList()
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
    // Dispose of any resources that can be recreated.
  }
  
  override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
    // Get the new view controller using segue.destinationViewController.
    let nextController = segue.destination
    // Pass the selected object to the new view controller.
  }
  
  func refreshProductList() {
    
    StoreAPI.shared.getStoreProducts() {
      modelList in
      
      guard let list = modelList else {
        print("Error in request")
        return
      }
      
      var newViewModelList = [ProductViewModel]()
      for item in list {
        newViewModelList.append(ProductViewModel(item))
      }
      self.productViewModel = newViewModelList
      self.productCollectionView.reloadData()
    }
  }
  
  @IBAction func exitButtonTouched() {
    self.dismiss(animated: true, completion: nil)
  }
}

extension StoreViewController: UICollectionViewDelegate {
  
  func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
    
    print("bu")
  }
  
  func collectionView(_ collectionView: UICollectionView, didDeselectItemAt indexPath: IndexPath) {
    print("bua")
  }
}

extension StoreViewController: UICollectionViewDataSource {
  
  func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
    guard let list = self.productViewModel else {
      return UICollectionViewCell()
    }
    
    let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "productCell", for: indexPath) as! ProductCollectionViewCell
    
    if let imageURL = list[indexPath.item].imageURL {
      cell.productImageView.af_setImage(withURL: imageURL)
    }
    cell.nameLabel.text = list[indexPath.item].name
    cell.priceLabel.text = list[indexPath.item].price
    cell.sellerLabel.text = list[indexPath.item].seller
    
    return cell
  }
  
  func numberOfSections(in collectionView: UICollectionView) -> Int {
    return 1
  }
  
  func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
    guard let list = self.productViewModel else {
      return 0
    }
    return list.count
  }
}
