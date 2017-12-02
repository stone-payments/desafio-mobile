//
//  AddCardViewController.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 01/12/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import UIKit

class AddCardViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource, UITextFieldDelegate {
	
	private let months = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"]
	private var years: [Int]!
	
	private var selectedMonth: Int!
	private var selectedYear: Int!

	@IBOutlet weak var scrollView: UIScrollView!
	@IBOutlet weak var holderNameField: UITextField!
	@IBOutlet weak var numberField: UITextField!
	@IBOutlet weak var cvvField: UITextField!
	@IBOutlet weak var expirationDatePicker: UIPickerView!
	@IBOutlet weak var saveButton: UIButton!
	@IBOutlet weak var backButton: UIButton!
	
	override func viewDidLoad() {
        super.viewDidLoad()
				
		saveButton.layer.cornerRadius = 5
		
		years = []
		let currentYear = Calendar.current.component(.year, from: Date())
		for year in currentYear...currentYear + 20 {
			years.append(year)
		}
		selectedYear = currentYear
		
        expirationDatePicker.delegate = self
		expirationDatePicker.dataSource = self
		
		let currentMonth = Calendar.current.component(.month, from: Date()) - 1
		expirationDatePicker.selectRow(currentMonth, inComponent: 0, animated: false)
		selectedMonth = currentMonth
		
		holderNameField.delegate = self
		numberField.delegate = self
		cvvField.delegate = self
		holderNameField.becomeFirstResponder()
    }
	
	// MARK: - Expiration Date Picker Delegate
	
	func numberOfComponents(in _: UIPickerView) -> Int {
		return 2
	}
	
	func pickerView(_: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
		if component == 0 {
			return months.count
		}
		else {
			return years.count
		}
	}
	
	func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
		var attributedString: NSAttributedString!
		
		if component == 0 {
			attributedString = NSAttributedString(string: months[row], attributes: [
				NSAttributedStringKey.foregroundColor : UIColor(red: 255/255, green: 232/255, blue: 31/255, alpha: 1)
				])
		}
		else {
			attributedString = NSAttributedString(string: String(years[row]), attributes: [
				NSAttributedStringKey.foregroundColor : UIColor(red: 255/255, green: 232/255, blue: 31/255, alpha: 1),
				NSAttributedStringKey.font : UIFont(name: "AvenirNext-Regular", size: 12)!
				])
		}
		
		return attributedString
	}
	
	func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
		if component == 0 {
			selectedMonth = row + 1
		}
		else {
			selectedYear = years[row]
		}
	}
	
	// MARK: - TextField Delegate
	
	func textFieldShouldReturn(_ textField: UITextField) -> Bool {
		if textField == holderNameField {
			numberField.becomeFirstResponder()
			scrollView.scrollRectToVisible(numberField.frame, animated: true)
		}
		else if textField == numberField {
			cvvField.becomeFirstResponder()
			scrollView.scrollRectToVisible(cvvField.frame, animated: true)
		}
		else if textField == cvvField {
			expirationDatePicker.becomeFirstResponder()
			scrollView.scrollRectToVisible(expirationDatePicker.frame, animated: true)
		}
		
		return true
	}
	
	// MARK: - Buttons' Action

	@IBAction func cancel(_ sender: Any) {
		dismiss(animated: true, completion: nil)
	}
	
	@IBAction func saveNewCard(_ sender: Any) {
		let alertView = UIAlertController(title: "", message: "", preferredStyle: .alert)
		alertView.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
		
		if let holderName = holderNameField.text, let numberString = numberField.text, let cvv = cvvField.text	{
			if holderName == "" {
				alertView.title = "Erro no nome do cartão"
				alertView.message = "Preencha corretamente o nome."
				
				present(alertView, animated: true, completion: nil)
			}
			else if numberString.count != 16 {
				alertView.title = "Erro no número do cartão"
				alertView.message = "O número deve conter 16 dígitos."
				
				present(alertView, animated: true, completion: nil)
			}
			else if cvv.count != 3 {
				alertView.title = "Erro no Código de segurança"
				alertView.message = "O CVV deve conter apenas 3 dígitos."
				
				present(alertView, animated: true, completion: nil)
			}
			else {
				var dateComponents = DateComponents()
				dateComponents.month = selectedMonth
				dateComponents.year = selectedYear
				let expirationDate = Calendar.current.date(from: dateComponents)
				
				User.shared.addCard(withNumber: numberString, cvv: Int16(cvv)!, holderName: holderName, expirationDate: expirationDate!)
				
				dismiss(animated: true, completion: nil)
			}
		}
	}
	
}
