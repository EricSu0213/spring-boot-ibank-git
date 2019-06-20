function getContextPath() {
    var ctx = window.location.pathname,
        path = '/' !== ctx ? ctx.substring(0, ctx.indexOf('/', 1) + 1) : ctx;
    return path + (/\/$/.test(path) ? '' : '/');
}