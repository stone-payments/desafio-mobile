//
//  ProductsListCollectionViewController.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 21/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit
import AlamofireImage

class ProductsListCollectionViewController: UICollectionViewController {

    var presenter: ProductsListPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialize()
    }
}

// MARK: - CollectionView -

extension ProductsListCollectionViewController: UICollectionViewDelegateFlowLayout {

    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return presenter.products.count
    }
    
    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ListItemViewCell", for: indexPath) as! ListItemViewCell
        
        let item: Product = presenter.products[indexPath.row]
        
        cell.itemDescription.text = item.title
        cell.itemPrice.text = item.price.toCurrencyString
        cell.itemImage.af_setImage(withURL: URL(string: item.thumbnail)!)
        cell.itemImage.backgroundColor = UIColor.clear
        
        cell.itemAdded(isAdded: false)
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let cellsAcross: CGFloat = 2
        let spaceBetweenCells: CGFloat = 7
        let newWidth = (collectionView.bounds.width - (cellsAcross + 1) * spaceBetweenCells) / cellsAcross
        return CGSize(width: newWidth, height: 200)
    }
}

// MARK: - Protocol methods -

extension ProductsListCollectionViewController: ProductsListViewProtocol {
    
    func showLoading() {}
    func hideLoading() {}
    
    func reloadCollectionView() {
        UIView.transition(with: collectionView!, duration: 0.35, options: .transitionCrossDissolve, animations: {
            self.collectionView?.reloadData() })
    }
    
    func addItem(at: IndexPath){}
    func showAlertError(with title: String, message: String, buttonTitle: String){}
}

// MARK: - Private methods -

extension ProductsListCollectionViewController {
    
    fileprivate func initialize() {
        presenter = ProductsListPresenter(view: self)
        presenter.showProductsList()
    }
}
