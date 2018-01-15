import ssSideNavSections from "./ssSideNavSections";

confSsSideNavSections.$inject = ['$mdThemingProvider', 'ssSideNavSectionsProvider'];
function confSsSideNavSections($mdThemingProvider, ssSideNavSectionsProvider) {
    ssSideNavSectionsProvider.initWithTheme($mdThemingProvider);
    ssSideNavSectionsProvider.initWithSections(ssSideNavSections);
}

export default confSsSideNavSections ;
