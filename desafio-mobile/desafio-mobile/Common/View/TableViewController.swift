//
//  TableViewController.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//


import UIKit


class TableViewController: UIViewController {
    
    var sections: [Section] = []
    
    @IBOutlet weak var tableView: UITableView!
    
    fileprivate func registerCell(section: Section) {
        
        let cellClass = section.tableViewCell()
        let nib = UINib(nibName: cellClass.defaultReuseIdentifier, bundle: nil)
        tableView.register(nib, forCellReuseIdentifier: cellClass.defaultReuseIdentifier)
    }
}


extension TableViewController: UITableViewDelegate, UITableViewDataSource {
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return self.sections.count
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.sections[section].items.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let section = sections[indexPath.section]
        registerCell(section: section)
        
        let identifier = section.tableViewCell().defaultReuseIdentifier
        return tableView.dequeueReusableCell(withIdentifier: identifier, for: indexPath)
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        sections[indexPath.section].didSelectRow(atIndexPath: indexPath)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return sections[indexPath.section].heightForCell(atIndexPath: indexPath)
    }
    
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        sections[indexPath.section].willDisplayCell(cell, atIndexPath: indexPath)
    }
}


extension TableViewController: SectionOutput {
    
    func reloadSection(_ section: Section) {
        guard let index = self.sections.index(of: section) else { return }
        self.tableView.reloadSections(IndexSet(integer: index), with: .automatic)
    }
    
    func insertItems(atSection section: Section) {
        
        guard let index = sections.index(of: section) else { return }
        tableView.insertItems(atSection: index, newCount: section.items.count)
    }
}
