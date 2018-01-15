
confUrlMatcher.$inject = ['$urlMatcherFactoryProvider'];
function confUrlMatcher($urlMatcherFactoryProvider) {
    $urlMatcherFactoryProvider.strictMode(false);
}


export default confUrlMatcher;
