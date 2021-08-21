import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class RoundUpdatePage {
  pageTitle: ElementFinder = element(by.id('placementServerApp.round.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  startDateInput: ElementFinder = element(by.css('input#round-startDate'));
  endDateInput: ElementFinder = element(by.css('input#round-endDate'));
  durationInput: ElementFinder = element(by.css('input#round-duration'));
  skillsRequiredInput: ElementFinder = element(by.css('input#round-skillsRequired'));
  linkInput: ElementFinder = element(by.css('input#round-link'));

  getPageTitle() {
    return this.pageTitle;
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

  async setDurationInput(duration) {
    await this.durationInput.sendKeys(duration);
  }

  async getDurationInput() {
    return this.durationInput.getAttribute('value');
  }

  async setSkillsRequiredInput(skillsRequired) {
    await this.skillsRequiredInput.sendKeys(skillsRequired);
  }

  async getSkillsRequiredInput() {
    return this.skillsRequiredInput.getAttribute('value');
  }

  async setLinkInput(link) {
    await this.linkInput.sendKeys(link);
  }

  async getLinkInput() {
    return this.linkInput.getAttribute('value');
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
    await this.setStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    await waitUntilDisplayed(this.saveButton);
    await this.setEndDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    await waitUntilDisplayed(this.saveButton);
    await this.setDurationInput('PT12S');
    await waitUntilDisplayed(this.saveButton);
    await this.setSkillsRequiredInput('skillsRequired');
    await waitUntilDisplayed(this.saveButton);
    await this.setLinkInput('link');
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
