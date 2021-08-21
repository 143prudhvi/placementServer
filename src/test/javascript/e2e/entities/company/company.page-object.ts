import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import CompanyUpdatePage from './company-update.page-object';

const expect = chai.expect;
export class CompanyDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('placementServerApp.company.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-company'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class CompanyComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('company-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('company');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateCompany() {
    await this.createButton.click();
    return new CompanyUpdatePage();
  }

  async deleteCompany() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const companyDeleteDialog = new CompanyDeleteDialog();
    await waitUntilDisplayed(companyDeleteDialog.deleteModal);
    expect(await companyDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/placementServerApp.company.delete.question/);
    await companyDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(companyDeleteDialog.deleteModal);

    expect(await isVisible(companyDeleteDialog.deleteModal)).to.be.false;
  }
}
