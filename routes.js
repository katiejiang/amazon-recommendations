Router.route('/', function () {
    Session.set('view', 'search');
    this.render('main');
});
