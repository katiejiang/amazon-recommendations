import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';

import './main.html';

Template.main.helpers({
    view : function () {
        return Session.get('view');
    }
})
