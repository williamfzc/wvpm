(function() {
    var attrList = [
        'navigationStart',
        'unloadEventStart',
        'unloadEventEnd',
        'redirectStart',
        'redirectEnd',
        'fetchStart',
        'domainLookupStart',
        'domainLookupEnd',
        'connectStart',
        'connectEnd',
        'secureConnectionStart',
        'requestStart',
        'responseStart',
        'responseEnd',
        'domLoading',
        'domInteractive',
        'domContentLoadedEventStart',
        'domContentLoadedEventEnd',
        'domComplete',
        'loadEventStart',
        'loadEventEnd'
    ]
    summary = {}
    for (var index in attrList) {
        attr = attrList[index]
        summary[attr] = window.performance.timing[attr]
    }
    return summary
})();
