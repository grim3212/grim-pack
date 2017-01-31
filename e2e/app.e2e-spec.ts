import { GrimPackSitePage } from './app.po';

describe('grim-pack-site App', function() {
  let page: GrimPackSitePage;

  beforeEach(() => {
    page = new GrimPackSitePage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
