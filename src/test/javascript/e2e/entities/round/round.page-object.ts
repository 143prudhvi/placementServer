import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import RoundUpdatePage from './round-update.page-object';

const expect = chai.expect;
export class RoundDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('placementServerApp.round.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-round'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class RoundComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('round-heading'));
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
    await navBarPage.getEntityPage('round');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateRound() {
    await this.createButton.click();
    return new RoundUpdatePage();
  }

  async deleteRound() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const roundDeleteDialog = new RoundDeleteDialog();
    await waitUntilDisplayed(roundDeleteDialog.deleteModal);
    expect(await roundDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/placementServerApp.round.delete.question/);
    await roundDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(roundDeleteDialog.deleteModal);

    expect(await isVisible(roundDeleteDialog.deleteModal)).to.be.false;
  }
}
