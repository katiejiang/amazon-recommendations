import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';

import './main.html';

Template.main.helpers({
    view : function () {
        return Session.get('view');
    }
})

Template.header.events({
    'submit #search-product-form': function (event) {
        event.preventDefault();
        var query = event.target.searchQuery.value;
        if (query) {
            query = new RegExp(query, 'i');
            var products = Products.find({'id': {$regex: query}}).fetch();
            Session.set('products', products);
            console.log(Session.get('products'));
        } else {
            Session.set('products', []);
        }
        Router.go('/');
    }
})

Template.search.helpers({
    products : function () {
        return Session.get('products');
    }
})

Template.product.helpers({
    product : function () {
        return Session.get('product');
    }
})

Template.productList.helpers({
    products : function () {
        return Session.get('products');
    }
})

Template.productList.events({
    'click .recommendations-btn' : function (event) {
        var id = event.target.id;
        if (id) {
            Router.go('/' + id);
        }
    }
})
