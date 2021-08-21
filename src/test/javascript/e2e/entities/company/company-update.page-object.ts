import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CompanyUpdatePage {
  pageTitle: ElementFinder = element(by.id('placementServerApp.company.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  companyNameInput: ElementFinder = element(by.css('input#company-companyName'));
  startDateInput: ElementFinder = element(by.css('input#company-startDate'));
  endDateInput: ElementFinder = element(by.css('input#company-endDate'));
  companyDetailsInput: ElementFinder = element(by.css('input#company-companyDetails'));
  placementTypeSelect: ElementFinder = element(by.css('select#company-placementType'));
  salaryPackageInput: ElementFinder = element(by.css('input#company-salaryPackage'));
  stageSelect: ElementFinder = element(by.css('select#company-stage'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setCompanyNameInput(companyName) {
    await this.companyNameInput.sendKeys(companyName);
  }

  async getCompanyNameInput() {
    return this.companyNameInput.getAttribute('value');
  }

  async setStartDateInput(startDate) {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput() {
    return this.startDateInput.getAttribute('value');
  }

  async setEndDateInput(endDate) {
    await this.endDateInput.sendKeys(endDate);
  }

  async getEndDateInput() {
    return this.endDateInput.getAttribute('value');
  }

  async setCompanyDetailsInput(companyDetails) {
    await this.companyDetailsInput.sendKeys(companyDetails);
  }

  async getCompanyDetailsInput() {
    return this.companyDetailsInput.getAttribute('value');
  }

  async setPlacementTypeSelect(placementType) {
    await this.placementTypeSelect.sendKeys(placementType);
  }

  async getPlacementTypeSelect() {
    return this.placementTypeSelect.element(by.css('option:checked')).getText();
  }

  async placementTypeSelectLastOption() {
    await this.placementTypeSelect.all(by.tagName('option')).last().click();
  }
  async setSalaryPackageInput(salaryPackage) {
    await this.salaryPackageInput.sendKeys(salaryPackage);
  }

  async getSalaryPackageInput() {
    return this.salaryPackageInput.getAttribute('value');
  }

  async setStageSelect(stage) {
    await this.stageSelect.sendKeys(stage);
  }

  async getStageSelect() {
    return this.stageSelect.element(by.css('option:checked')).getText();
  }

  async stageSelectLastOption() {
    await this.stageSelect.all(by.tagName('option')).last().click();
  }
  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setCompanyNameInput('companyName');
    await waitUntilDisplayed(this.saveButton);
    await this.setStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    await waitUntilDisplayed(this.saveButton);
    await this.setEndDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    await waitUntilDisplayed(this.saveButton);
    await this.setCompanyDetailsInput('companyDetails');
    await waitUntilDisplayed(this.saveButton);
    await this.placementTypeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setSalaryPackageInput('salaryPackage');
    await waitUntilDisplayed(this.saveButton);
    await this.stageSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
