/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export const collectApiErrors = (error) => {
    let apiError = error.response.data.apierror;
    let root = {
        message: error.message,
        status: error.response.status,
        statusText: error.response.statusText,
        nested: {}
    };
    if (apiError) {
        root.message = apiError.message;
        (apiError.nestedErrors || [])
            .reduce((acc, cv) => Object.assign(acc, {[cv.field]: cv.message}), root.nested);
    }
    return root;
};
