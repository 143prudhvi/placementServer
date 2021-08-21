import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import RoundComponentsPage from './round.page-object';
import RoundUpdatePage from './round-update.page-object';
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

describe('Round e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let roundComponentsPage: RoundComponentsPage;
  let roundUpdatePage: RoundUpdatePage;
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
    roundComponentsPage = new RoundComponentsPage();
    roundComponentsPage = await roundComponentsPage.goToPage(navBarPage);
  });

  it('should load Rounds', async () => {
    expect(await roundComponentsPage.title.getText()).to.match(/Rounds/);
    expect(await roundComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Rounds', async () => {
    const beforeRecordsCount = (await isVisible(roundComponentsPage.noRecords)) ? 0 : await getRecordsCount(roundComponentsPage.table);
    roundUpdatePage = await roundComponentsPage.goToCreateRound();
    await roundUpdatePage.enterData();
    expect(await isVisible(roundUpdatePage.saveButton)).to.be.false;

    expect(await roundComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(roundComponentsPage.table);
    await waitUntilCount(roundComponentsPage.records, beforeRecordsCount + 1);
    expect(await roundComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await roundComponentsPage.deleteRound();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(roundComponentsPage.records, beforeRecordsCount);
      expect(await roundComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(roundComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
