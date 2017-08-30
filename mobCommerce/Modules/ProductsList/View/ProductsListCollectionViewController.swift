//
//  ProductsListCollectionViewController.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 21/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit
import AlamofireImage
import SVProgressHUD

class ProductsListCollectionViewController: UICollectionViewController {

    var presenter: ProductsListPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialize()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        presenter.showProductsList()
        self.reloadCollectionView()
    }
}

// MARK: - UICollectionView -

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
        
        cell.actionButton.tag = indexPath.row
        cell.actionButton.addTarget(self, action: #selector(addProductToCart), for: .touchUpInside)
        
        cell.itemAdded(isAdded: presenter.itemPurchased(with: item.title))
        
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
    
    func showLoading() {
        SVProgressHUD.show()
    }
    
    func hideLoading() {
        SVProgressHUD.dismiss()
    }
    
    func reloadCollectionView() {
        UIView.transition(with: collectionView!, duration: 0.35, options: .transitionCrossDissolve, animations: {
            self.collectionView?.reloadData() })
    }
    
    func reloadCollectionViewCell(at indexPath: IndexPath) {
        self.collectionView?.reloadItems(at: [indexPath])
    }
    
    func updateBadgeToValue(with value: String) {
        self.navigationController?.tabBarController?.tabBar.items![1].badgeValue = value
    }
    
    func showAlertError(with title: String, message: String, buttonTitle: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: buttonTitle, style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
}

// MARK: - Private methods -

extension ProductsListCollectionViewController {
    
    fileprivate func initialize() {
        presenter = ProductsListPresenter(view: self)
    }
}

// MARK: - Action methods -

extension ProductsListCollectionViewController {
    
    @IBAction func addProductToCart(_ sender: UIButton) {
        presenter.buyItem(with: presenter.products[sender.tag], at: sender.tag)
    }
    
}
