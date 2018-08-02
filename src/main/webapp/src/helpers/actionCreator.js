/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
const actionCreator = (type, ...argNames) => (...args) => {
    const action = {type};
    argNames.forEach((arg, index) => {
        action[argNames[index]] = args[index];
    });
    return action;
};

export default actionCreator