import { GrimPackPage } from './app.po';

describe('grim-pack App', function() {
  let page: GrimPackPage;

  beforeEach(() => {
    page = new GrimPackPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
