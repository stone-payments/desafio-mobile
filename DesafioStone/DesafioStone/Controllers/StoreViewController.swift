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
    self.productCollectionView.allowsMultipleSelection = true

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
    let nextController = segue.destination as! CartViewController
    
    if let selectedItens = self.productCollectionView.indexPathsForSelectedItems,
      let productList = self.productViewModel {
      
      var totalAmount: Double = 0.0
      for index in selectedItens {
        totalAmount += productList[index.item].model.price
      }
      
      nextController.totalAmount = totalAmount.formatedAsCurreny()
    }
    
    
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
    
    let cell = collectionView.cellForItem(at: indexPath)
    cell?.backgroundColor = UIColor(red: 229/255.0, green: 177/255.0, blue: 58/255.0, alpha: 0.5)
  }
  
  func collectionView(_ collectionView: UICollectionView, didDeselectItemAt indexPath: IndexPath) {
    
    let cell = collectionView.cellForItem(at: indexPath)
    cell?.backgroundColor = UIColor(red: 225/255.0, green: 225/255.0, blue: 225/255.0, alpha: 1.0)
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
    
    if collectionView.indexPathsForSelectedItems?.contains(indexPath) == true {
      cell.backgroundColor = UIColor(red: 229/255.0, green: 177/255.0, blue: 58/255.0, alpha: 0.5)
    } else {
      cell.backgroundColor = UIColor(red: 225/255.0, green: 225/255.0, blue: 225/255.0, alpha: 1.0)
    }
    
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
