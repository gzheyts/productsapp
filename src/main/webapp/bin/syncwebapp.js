/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
const rsyncWrapper = require('rsync');
new rsyncWrapper()
    .flags(['compress', 'force', 'recursive', 'delete'])
    .source('build/')
    .destination('../resources/public/')
    .execute(function (error, code, cmd) {
        console.log("Done", cmd);
    });
