call ng build --prod --base-href /grim-pack/

copy "dist\index.html" "dist\404.html"

call angular-cli-ghpages --message="DEcor changes"

pause