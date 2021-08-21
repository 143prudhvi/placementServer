import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CompanyComponentsPage from './company.page-object';
import CompanyUpdatePage from './company-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('Company e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyComponentsPage: CompanyComponentsPage;
  let companyUpdatePage: CompanyUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    companyComponentsPage = new CompanyComponentsPage();
    companyComponentsPage = await companyComponentsPage.goToPage(navBarPage);
  });

  it('should load Companies', async () => {
    expect(await companyComponentsPage.title.getText()).to.match(/Companies/);
    expect(await companyComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Companies', async () => {
    const beforeRecordsCount = (await isVisible(companyComponentsPage.noRecords)) ? 0 : await getRecordsCount(companyComponentsPage.table);
    companyUpdatePage = await companyComponentsPage.goToCreateCompany();
    await companyUpdatePage.enterData();
    expect(await isVisible(companyUpdatePage.saveButton)).to.be.false;

    expect(await companyComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(companyComponentsPage.table);
    await waitUntilCount(companyComponentsPage.records, beforeRecordsCount + 1);
    expect(await companyComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await companyComponentsPage.deleteCompany();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(companyComponentsPage.records, beforeRecordsCount);
      expect(await companyComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(companyComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
