/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export function formErrors(state, name, errors) {
    let {forms} = state;
    return {forms: {...forms, [name]: {...forms[name], errors}}};
}

export function formValues(state, name, values) {
    let {forms} = state;
    return {forms: {...forms, [name]: {...forms[name], values}}};
}

export function formToggle(state, name) {
    let {forms} = state;
    return {forms: {...forms, [name]: {...forms[name], isOpen: !forms[name].isOpen}}};
}

export function request(state) {
    return {...state, isLoading: state.content.length !== 0}
}

export function fail(state) {
    return {...state, isLoading: false, errors: state.errors};
}

export function success(state) {
    return {...state, isLoading: false};
}

export function merge(obj, values) {
    return {...obj, ...values};
}

