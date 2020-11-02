package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.Easing.Easing;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

/*
function generateMethod(method, accept) {
    let modifiers = method.mod.join(" ");
    let ret = (method.ret || accept) + "[]";
    let name = method.name;
    let args = `(${(() => {
        let res = "";
        for(let arg of method.args) {
            res += `${arg.type || accept}${arg.array ? "[]" : ""} ${arg.name}, `;
            if(arg.array && method.additional) res += `int ${arg.name}Off, `;
        }
        res = method.additional ? `${res}int len` : res.substr(0, res.length - 2);
        return res;
    })()})`;
    const methodDef = [modifiers, ret, name].filter(val => !!val).join(" ") + args;

    let compareTo;
    for(let arg of method.args)
        if(arg.array) compareTo = arg;
    let asserts = (() => {
        let res = method.additional ? "" : "assert ";
        if(!method.additional && !compareTo)
            return "";
        let compared = false;
        for(let arg of method.args) {
            if(!arg.array) continue;
            if(method.additional) res += `ArrayUtils.assertIndex(${arg.name}Off, ${arg.name}.length, len); `;
            else if(arg != compareTo) { res += `${arg.name}.length == ${compareTo.name}.length && `; compared = true; }
        }
        if(!method.additional && !compared) return "";
        res = method.additional ? res.substr(0, res.length - 1) : res.substr(0, res.length - 3) + ": \"Length is not equal!\";"
        return res;
    })();
    let fit = method.group ? `int fit = ${method.additional ? `(len / ${method.group}) * ${method.group}` : `(${compareTo.name}.length / ${method.group}) * ${method.group}`};` : "";
    let indexes = (() => {
        let res = "int i = 0; ";
        if(method.additional) for(let arg of method.args)
            if(arg.array) res += `int ${arg.name}I = ${arg.name}Off; `
        return res.substr(0, res.length - 1);
    })();
    let forLoop = (() => {
        let statementBlock = (rule, printCondition) => {
            let _res = method.group ? (() => {
                let forHead = `for(; i < ${method.additional ? "fit" : `${compareTo.name}.length`}; i += ${method.group}, `;
                if(method.additional) for(let arg of method.args) if(arg.array) forHead += `${arg.name}I += ${method.group}, `;
                forHead = forHead.substr(0, forHead.length - 2) + ")";
                let forBody = [];
                for(let i = 0; i < method.group; i++)
                    forBody.push(`${compareTo.name}[${method.additional ? `${compareTo.name}I` : "i"} + ${i}] = (${method.ret || accept}) ${rule.inline ? `(${rule.inline(i, accept)})` : `${method.name}(${rule.callArgs(i)})`};`);
                return `${forHead} { ${forBody.join(" ")} }`;
            })() : "";
            _res += (_res.length ? " " : "") + (() => {
                let forHead = `for(; i < ${method.additional ? "len" : `${compareTo.name}.length`}; i++, `;
                if(method.additional) for(let arg of method.args) if(arg.array) forHead += `${arg.name}I++, `;
                forHead = forHead.substr(0, forHead.length - 2) + ")";
                let forBody = `${compareTo.name}[${method.additional ? `${compareTo.name}I` : "i"} + ${0}] = (${method.ret || accept}) ${rule.inline ? `(${rule.inline(0, accept)})` : `${method.name}(${rule.callArgs(0)})`};`;
                return `${forHead} ${forBody}`;
            })();
            return `${printCondition ? `if(${rule.condition}) ` : ""}{ ${_res} }`;
        };
        if(!method.rules) return statementBlock(method, false);
        const blocks = [];
        for(let rule of method.rules)
            blocks.push(statementBlock(rule, !!rule.condition));
        return blocks.join(" else ");
    })();
    let retVal = `return ${compareTo.name};`;
    const methodBod = [asserts, fit, indexes, forLoop, retVal].filter(val => !!val).join(" ");
    return `${methodDef} { ${methodBod} }`;
}
function generateDefault1Method(method, accept) {
    if(!method.additional) return "";
    let modifiers = method.mod.join(" ");
    let ret = (method.ret || accept) + "[]";
    let name = method.name;
    let args = `(${(() => {
        let res = "";
        for(let arg of method.args) {
            res += `${arg.type || accept}${arg.array ? "[]" : ""} ${arg.name}, `;
            if(arg.array && method.additional) res += `int ${arg.name}Off, `;
        }
        res = res.substr(0, res.length - 2);
        return res;
    })()})`;
    const methodDef = [modifiers, ret, name].filter(val => !!val).join(" ") + args;

    let compareTo;
    for(let arg of method.args)
        if(arg.array) compareTo = arg;
    let call = `${ret != "void" ? "return" : ""} ${name}(${(() => {
        let res = "";
        for(let arg of method.args) {
            res += `${arg.name}, `;
            if(arg.array && method.additional) res += `${arg.name}Off, `;
        }
        res += `${compareTo.name}.length - ${compareTo.name}Off`
        return res;
    })()});`;
    const methodBod = [call].filter(val => !!val).join(" ");
    return `${methodDef} { ${methodBod} }`;
}
function generateDefault2Method(method, accept) {
    if(!method.additional) return "";
    let modifiers = method.mod.join(" ");
    let ret = (method.ret || accept) + "[]";
    let name = method.name;
    let args = `(${(() => {
        let res = "";
        for(let arg of method.args) {
            res += `${arg.type || accept}${arg.array ? "[]" : ""} ${arg.name}, `;
        }
        res = res.substr(0, res.length - 2);
        return res;
    })()})`;
    const methodDef = [modifiers, ret, name].filter(val => !!val).join(" ") + args;

    let compareTo;
    for(let arg of method.args)
        if(arg.array) compareTo = arg;
    let call = `${ret != "void" ? "return" : ""} ${name}(${(() => {
        let res = "";
        for(let arg of method.args) {
            res += `${arg.name}, `;
            if(arg.array && method.additional) res += `0, `;
        }
        return res.substr(0, res.length - 2);
    })()});`;
    const methodBod = [call].filter(val => !!val).join(" ");
    return `${methodDef} { ${methodBod} }`;
}
function generateDefault3Method(method, accept) {
    const result = [];
    let defaults = [];
    let defaultsIs = [];
    for(let _a = 0; _a < method.args.length; _a++)
        if(method.args[_a].defaults) { defaults.push(method.args[_a]); defaultsIs.push(_a); }
    let defaultsI = defaults.length - 1;
    while(defaultsI >= 0) {
        let modifiers = method.mod.join(" ");
        let ret = (method.ret || accept) + "[]";
        let name = method.name;
        let args = `(${(() => {
            let res = "";
            for(let _a = 0; _a < method.args.length; _a++) { const arg = method.args[_a];
                if(defaultsIs.includes(_a) && _a >= defaultsIs[defaultsI]) continue;
                res += `${arg.type || accept}${arg.array ? "[]" : ""} ${arg.name}, `;
            }
            res = res.substr(0, res.length - 2);
            return res;
        })()})`;
        const methodDef = [modifiers, ret, name].filter(val => !!val).join(" ") + args;

        let call = `${ret != "void" ? "return" : ""} ${name}(${(() => {
            let res = "";
            for(let _a = 0; _a < method.args.length; _a++) { const arg = method.args[_a];
                if(defaultsIs.includes(_a) && _a >= defaultsIs[defaultsI]) { res += `${arg.defaults}, `; continue; }
                res += `${arg.name}, `;
            }
            res = res.substr(0, res.length - 2);
            return res;
        })()});`;
        const methodBod = [call].filter(val => !!val).join(" ");
        result.push(`${methodDef} { ${methodBod} }`);
        defaultsI--;
    }
    return result;
}
function generateDefault4Method(method, accept) {
    if(!method.additional) return "";
    let compareTo;
    for(let arg of method.args)
        if(arg.array) compareTo = arg;

    let modifiers = method.mod.join(" ");
    let ret = (method.ret || accept) + "[]";
    let name = method.name;
    let args = `(${(() => {
        let res = "";
        for(let arg of method.args) {
            if(arg == compareTo) continue;
            res += `${arg.type || accept}${arg.array ? "[]" : ""} ${arg.name}, `;
        }
        res = res.substr(0, res.length - 2);
        return res;
    })()})`;
    const methodDef = [modifiers, ret, name].filter(val => !!val).join(" ") + args;

    let found = false;
    let call = `${ret != "void" ? "return" : ""} ${name}(${(() => {
        let res = "";
        for(let arg of method.args) {
            if(arg == compareTo) {
                let sum = (() => {
                    let _res = [];
                    for(let _arg of method.args) {
                        if(_arg == compareTo) continue;
                        if(!_arg.array) continue;
                        _res.push(`${_arg.name}.length`);
                    } return _res;
                })(); found = sum.length != 0;
                res += `new ${compareTo.type || accept}[(${sum.join(" + ")}) / ${sum.length}], `;
                continue;
            }
            res += `${arg.name}, `;
        }
        return res.substr(0, res.length - 2);
    })()});`;
    const methodBod = [call].filter(val => !!val).join(" ");
    return found ? `${methodDef} { ${methodBod} }` : "";
}
function generateMethodDefinition(method, accept) {
    let modifiers = method.mod.join(" ");
    let ret = (method.ret || accept) + "[]";
    let name = method.name;
    let args = `(${(() => {
        let res = "";
        for(let arg of method.args) {
            res += `${arg.type || accept}${arg.array ? "[]" : ""} ${arg.name}, `;
            if(arg.array && method.additional) res += `int ${arg.name}Off, `;
        }
        res = method.additional ? `${res}int len` : res.substr(0, res.length - 2);
        return res;
    })()})`;
    const methodDef = [modifiers, ret, name].filter(val => !!val).join(" ") + args;

    let compareTo;
    for(let arg of method.args)
        if(arg.array) compareTo = arg;
    let asserts = (() => {
        let res = method.additional ? "" : "assert ";
        if(!method.additional && !compareTo)
            return "";
        let compared = false;
        for(let arg of method.args) {
            if(!arg.array) continue;
            if(method.additional) res += `ArrayUtils.assertIndex(${arg.name}Off, ${arg.name}.length, len); `;
            else if(arg != compareTo) { res += `${arg.name}.length == ${compareTo.name}.length && `; compared = true; }
        }
        if(!method.additional && !compared) return "";
        res = method.additional ? res.substr(0, res.length - 1) : res.substr(0, res.length - 3) + ": \"Length is not equal!\";"
        return res;
    })();
    let fit = method.group ? `int fit = ${method.additional ? `(len / ${method.group}) * ${method.group}` : `(${compareTo.name}.length / ${method.group}) * ${method.group}`};` : "";
    let indexes = (() => {
        let res = "int i = 0; ";
        if(method.additional) for(let arg of method.args)
            if(arg.array) res += `int ${arg.name}I = ${arg.name}Off; `
        return res.substr(0, res.length - 1);
    })();
    let forLoop = (() => {
        let statementBlock = (rule, printCondition) => {
            let _res = method.group ? (() => {
                let forHead = `for(; i < ${method.additional ? "fit" : `${compareTo.name}.length`}; i += ${method.group}, `;
                if(method.additional) for(let arg of method.args) if(arg.array) forHead += `${arg.name}I += ${method.group}, `;
                forHead = forHead.substr(0, forHead.length - 2) + ")";
                let forBody = [];
                for(let i = 0; i < method.group; i++)
                    forBody.push(`${compareTo.name}[${method.additional ? `${compareTo.name}I` : "i"} + ${i}] = (${method.ret || accept}) ${rule.inline ? `(${rule.inline(i, accept)})` : `${method.name}(${rule.callArgs(i)})`};`);
                return `${forHead} { ${forBody.join(" ")} }`;
            })() : "";
            _res += (_res.length ? " " : "") + (() => {
                let forHead = `for(; i < ${method.additional ? "len" : `${compareTo.name}.length`}; i++, `;
                if(method.additional) for(let arg of method.args) if(arg.array) forHead += `${arg.name}I++, `;
                forHead = forHead.substr(0, forHead.length - 2) + ")";
                let forBody = `${compareTo.name}[${method.additional ? `${compareTo.name}I` : "i"} + ${0}] = (${method.ret || accept}) ${rule.inline ? `(${rule.inline(0, accept)})` : `${method.name}(${rule.callArgs(0)})`};`;
                return `${forHead} ${forBody}`;
            })();
            return `${printCondition ? `if(${rule.condition}) ` : ""}{ ${_res} }`;
        };
        if(!method.rules) return statementBlock(method, false);
        const blocks = [];
        for(let rule of method.rules)
            blocks.push(statementBlock(rule, !!rule.condition));
        return blocks.join(" else ");
    })();
    let retVal = `return ${compareTo.name};`;
    const methodBod = [asserts, fit, indexes, forLoop, retVal].filter(val => !!val).join(" ");
    return `${methodDef}`;
}
function generateDefault1MethodDefinition(method, accept) {
    if(!method.additional) return "";
    let modifiers = method.mod.join(" ");
    let ret = (method.ret || accept) + "[]";
    let name = method.name;
    let args = `(${(() => {
        let res = "";
        for(let arg of method.args) {
            res += `${arg.type || accept}${arg.array ? "[]" : ""} ${arg.name}, `;
            if(arg.array && method.additional) res += `int ${arg.name}Off, `;
        }
        res = res.substr(0, res.length - 2);
        return res;
    })()})`;
    const methodDef = [modifiers, ret, name].filter(val => !!val).join(" ") + args;

    let compareTo;
    for(let arg of method.args)
        if(arg.array) compareTo = arg;
    let call = `${ret != "void" ? "return" : ""} ${name}(${(() => {
        let res = "";
        for(let arg of method.args) {
            res += `${arg.name}, `;
            if(arg.array && method.additional) res += `${arg.name}Off, `;
        }
        res += `${compareTo.name}.length - ${compareTo.name}Off`
        return res;
    })()});`;
    const methodBod = [call].filter(val => !!val).join(" ");
    return `${methodDef}`;
}
function generateDefault2MethodDefinition(method, accept) {
    if(!method.additional) return "";
    let modifiers = method.mod.join(" ");
    let ret = (method.ret || accept) + "[]";
    let name = method.name;
    let args = `(${(() => {
        let res = "";
        for(let arg of method.args) {
            res += `${arg.type || accept}${arg.array ? "[]" : ""} ${arg.name}, `;
        }
        res = res.substr(0, res.length - 2);
        return res;
    })()})`;
    const methodDef = [modifiers, ret, name].filter(val => !!val).join(" ") + args;

    let compareTo;
    for(let arg of method.args)
        if(arg.array) compareTo = arg;
    let call = `${ret != "void" ? "return" : ""} ${name}(${(() => {
        let res = "";
        for(let arg of method.args) {
            res += `${arg.name}, `;
            if(arg.array && method.additional) res += `0, `;
        }
        return res.substr(0, res.length - 2);
    })()});`;
    const methodBod = [call].filter(val => !!val).join(" ");
    return `${methodDef}`;
}
function generateDefault3MethodDefinition(method, accept) {
    const result = [];
    let defaults = [];
    let defaultsIs = [];
    for(let _a = 0; _a < method.args.length; _a++)
        if(method.args[_a].defaults) { defaults.push(method.args[_a]); defaultsIs.push(_a); }
    let defaultsI = defaults.length - 1;
    while(defaultsI >= 0) {
        let modifiers = method.mod.join(" ");
        let ret = (method.ret || accept) + "[]";
        let name = method.name;
        let args = `(${(() => {
            let res = "";
            for(let _a = 0; _a < method.args.length; _a++) { const arg = method.args[_a];
                if(defaultsIs.includes(_a) && _a >= defaultsIs[defaultsI]) continue;
                res += `${arg.type || accept}${arg.array ? "[]" : ""} ${arg.name}, `;
            }
            res = res.substr(0, res.length - 2);
            return res;
        })()})`;
        const methodDef = [modifiers, ret, name].filter(val => !!val).join(" ") + args;

        let call = `${ret != "void" ? "return" : ""} ${name}(${(() => {
            let res = "";
            for(let _a = 0; _a < method.args.length; _a++) { const arg = method.args[_a];
                if(defaultsIs.includes(_a) && _a >= defaultsIs[defaultsI]) { res += `${arg.defaults}, `; continue; }
                res += `${arg.name}, `;
            }
            res = res.substr(0, res.length - 2);
            return res;
        })()});`;
        const methodBod = [call].filter(val => !!val).join(" ");
        result.push(`${methodDef}`);
        defaultsI--;
    }
    return result;
}
function generateDefault4MethodDefinition(method, accept) {
    if(!method.additional) return "";
    let compareTo;
    for(let arg of method.args)
        if(arg.array) compareTo = arg;

    let modifiers = method.mod.join(" ");
    let ret = (method.ret || accept) + "[]";
    let name = method.name;
    let args = `(${(() => {
        let res = "";
        for(let arg of method.args) {
            if(arg == compareTo) continue;
            res += `${arg.type || accept}${arg.array ? "[]" : ""} ${arg.name}, `;
        }
        res = res.substr(0, res.length - 2);
        return res;
    })()})`;
    const methodDef = [modifiers, ret, name].filter(val => !!val).join(" ") + args;

    let found = false;
    let call = `${ret != "void" ? "return" : ""} ${name}(${(() => {
        let res = "";
        for(let arg of method.args) {
            if(arg == compareTo) {
                let sum = (() => {
                    let _res = [];
                    for(let _arg of method.args) {
                        if(_arg == compareTo) continue;
                        if(!_arg.array) continue;
                        _res.push(`${_arg.name}.length`);
                    } return _res;
                })(); found = sum.length != 0;
                res += `new ${compareTo.type || accept}[(${sum.join(" + ")}) / ${sum.length}], `;
                continue;
            }
            res += `${arg.name}, `;
        }
        return res.substr(0, res.length - 2);
    })()});`;
    const methodBod = [call].filter(val => !!val).join(" ");
    return found ? `${methodDef}` : "";
}

const methodsString = '[{"name":"rand","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"args":[{"name":"min","array":false},{"name":"max","array":false},{"type":"Random","name":"random","array":false,"defaults":"PublicRandom.getRandom()"},{"name":"target","array":true}],"callArgs":"_NuFrRa_(i) => { return `min, max, random` }","inline":"_NuFrRa_(i, accept) => `random.nextDouble() * (max - min) + min`"},{"name":"fma","mod":["public","static"],"group":4,"additional":true,"accepts":["float","double"],"args":[{"name":"a","array":true},{"name":"b","array":true},{"name":"c","array":true},{"name":"target","array":true}],"callArgs":"_NuFrRa_(i) => { return `a[aI + ${i}], b[bI + ${i}], c[cI + ${i}]` }","rules":[{"condition":"USE_FMA && FMA_INSTANCE != null","inline":"_NuFrRa_(i, accept) => `FMA_INSTANCE.fma(a[aI + ${i}], b[bI + ${i}], c[cI + ${i}])`"},{"inline":"_NuFrRa_(i, accept) => `a[aI + ${i}] * b[bI + ${i}] + c[cI + ${i}]`"}]},{"name":"fma","mod":["public","static"],"group":4,"additional":true,"accepts":["float","double"],"args":[{"name":"a","array":true},{"name":"b","array":true},{"name":"target","array":true}],"callArgs":"_NuFrRa_(i) => { return `a[aI + ${i}], b[bI + ${i}]` }","rules":[{"condition":"USE_FMA && FMA_INSTANCE != null","inline":"_NuFrRa_(i, accept) => `FMA_INSTANCE.fma(a[aI + ${i}], b[bI + ${i}], 0)`"},{"inline":"_NuFrRa_(i, accept) => `a[aI + ${i}] * b[bI + ${i}]`"}]},{"name":"radians","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"args":[{"name":"degrees","array":true},{"name":"target","array":true,"type":"double"}],"callArgs":"_NuFrRa_(i) => { return `degrees[degreesI + ${i}]` }","ret":"double","inline":"_NuFrRa_(i, accept) => `Math.toRadians(degrees[degreesI + ${i}])`"},{"name":"degrees","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"args":[{"name":"radians","array":true},{"name":"target","array":true,"type":"double"}],"callArgs":"_NuFrRa_(i) => { return `radians[radiansI + ${i}]` }","ret":"double","inline":"_NuFrRa_(i, accept) => `Math.toDegrees(radians[radiansI + ${i}])`"},{"name":"sin","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `angles[anglesI + ${i}]`; }","args":[{"name":"angles","array":true},{"name":"target","array":true,"type":"double"}],"ret":"double","inline":"_NuFrRa_(i, accept) => `Math.sin(angles[anglesI + ${i}])`"},{"name":"cos","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `angles[anglesI + ${i}]`; }","args":[{"name":"angles","array":true},{"name":"target","array":true,"type":"double"}],"ret":"double","inline":"_NuFrRa_(i, accept) => `Math.cos(angles[anglesI + ${i}])`"},{"name":"tan","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `angles[anglesI + ${i}]`; }","args":[{"name":"angles","array":true},{"name":"target","array":true,"type":"double"}],"ret":"double","inline":"_NuFrRa_(i, accept) => `Math.tan(angles[anglesI + ${i}])`"},{"name":"asin","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `y[yI + ${i}]`; }","args":[{"name":"y","array":true},{"name":"target","array":true,"type":"double"}],"ret":"double","inline":"_NuFrRa_(i, accept) => `Math.asin(y[yI + ${i}])`"},{"name":"acos","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}],"ret":"double","inline":"_NuFrRa_(i, accept) => `Math.acos(x[xI + ${i}])`"},{"name":"atan","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `y_over_x[y_over_xI + ${i}]`; }","args":[{"name":"y_over_x","array":true},{"name":"target","array":true,"type":"double"}],"ret":"double","inline":"_NuFrRa_(i, accept) => `Math.atan(y_over_x[y_over_xI + ${i}])`"},{"name":"atanYX","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `y[yI + ${i}], x[xI + ${i}]`; }","args":[{"name":"y","array":true},{"name":"x","array":true},{"name":"target","array":true,"type":"double"}],"ret":"double","inline":"_NuFrRa_(i, accept) => `Math.atan(y[yI + ${i}] / x[xI + ${i}])`"},{"name":"atan2","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `y[yI + ${i}], x[xI + ${i}]`; }","args":[{"name":"y","array":true},{"name":"x","array":true},{"name":"target","array":true,"type":"double"}],"ret":"double","inline":"_NuFrRa_(i, accept) => `Math.atan2(y[yI + ${i}], x[xI + ${i}])`"},{"name":"pow","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true}],"inline":"_NuFrRa_(i, accept) => `Math.pow(x[xI + ${i}], y[yI + ${i}])`"},{"name":"powD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.pow(x[xI + ${i}], y[yI + ${i}])`"},{"name":"exp","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.exp(x[xI + ${i}])`"},{"name":"log","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.log(x[xI + ${i}])`"},{"name":"exp2","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.pow(2, x[xI + ${i}])`"},{"name":"log2","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.log(x[xI + ${i}]) / Math.log(2)`"},{"name":"sqrt","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.sqrt(x[xI + ${i}])`"},{"name":"inversesqrt","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}]},{"name":"abs","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true}],"inline":"_NuFrRa_(i, accept) => `Math.abs(x[xI + ${i}])`"},{"name":"absD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.abs(x[xI + ${i}])`"},{"name":"sign","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true}]},{"name":"signD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}]},{"name":"nonZeroSign","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true}]},{"name":"nonZeroSignD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}]},{"name":"floor","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true}],"inline":"_NuFrRa_(i, accept) => `Math.floor(x[xI + ${i}])`"},{"name":"floorD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.floor(x[xI + ${i}])`"},{"name":"ceil","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true}],"inline":"_NuFrRa_(i, accept) => `Math.ceil(x[xI + ${i}])`"},{"name":"ceilD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.ceil(x[xI + ${i}])`"},{"name":"fract","mod":["public","static"],"group":4,"additional":true,"accepts":["float","double"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"target","array":true}]},{"name":"min","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true}],"inline":"_NuFrRa_(i, accept) => `Math.min(x[xI + ${i}], y[yI + ${i}])`"},{"name":"min","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y`; }","args":[{"name":"x","array":true},{"name":"y","array":false},{"name":"target","array":true}],"inline":"_NuFrRa_(i, accept) => `Math.min(x[xI + ${i}], y)`"},{"name":"minD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.min(x[xI + ${i}], y[yI + ${i}])`"},{"name":"minD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y`; }","args":[{"name":"x","array":true},{"name":"y","array":false},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.min(x[xI + ${i}], y)`"},{"name":"max","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true}],"inline":"_NuFrRa_(i, accept) => `Math.max(x[xI + ${i}], y[yI + ${i}])`"},{"name":"max","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y`; }","args":[{"name":"x","array":true},{"name":"y","array":false},{"name":"target","array":true}],"inline":"_NuFrRa_(i, accept) => `Math.max(x[xI + ${i}], y)`"},{"name":"maxD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.max(x[xI + ${i}], y[yI + ${i}])`"},{"name":"maxD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y`; }","args":[{"name":"x","array":true},{"name":"y","array":false},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.max(x[xI + ${i}], y)`"},{"name":"clamp","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], minVal[minValI + ${i}], maxVal[maxValI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"minVal","array":true},{"name":"maxVal","array":true},{"name":"target","array":true}]},{"name":"clamp","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], minVal, maxVal`; }","args":[{"name":"x","array":true},{"name":"minVal","array":false},{"name":"maxVal","array":false},{"name":"target","array":true}]},{"name":"clampD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], minVal[minValI + ${i}], maxVal[maxValI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"minVal","array":true},{"name":"maxVal","array":true},{"name":"target","array":true,"type":"double"}]},{"name":"clampD","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], minVal, maxVal`; }","args":[{"name":"x","array":true},{"name":"minVal","array":false},{"name":"maxVal","array":false},{"name":"target","array":true,"type":"double"}]},{"accepts":["int","long","short","float","double","char"],"additional":true,"args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"a","array":true},{"name":"target","array":true,"type":"double"}],"length":4,"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}], a[aI + ${i}]`; }","group":4,"mod":["public","static"],"name":"mix","ret":"double"},{"accepts":["int","long","short","float","double","char"],"additional":true,"args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"a","array":false},{"name":"target","array":true,"type":"double"}],"length":4,"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}], a`; }","group":4,"mod":["public","static"],"name":"mix","ret":"double"},{"name":"step","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], edge[edgeI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"edge","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `x[xI + ${i}] > edge[edgeI + ${i}] ? 1.0 : 0.0`"},{"name":"step","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], edge`; }","args":[{"name":"x","array":true},{"name":"edge","array":false},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `x[xI + ${i}] > edge ? 1.0 : 0.0`"},{"name":"smoothstep","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `edge0[edge0Off + ${i}], edge1[edge1Off + ${i}], x[xI + ${i}]`; }","args":[{"name":"edge0","array":true},{"name":"edge1","array":true},{"name":"x","array":true},{"name":"target","array":true,"type":"double"}]},{"name":"smoothstep","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `edge0[edge0Off + ${i}], edge1[edge1Off + ${i}], x`; }","args":[{"name":"edge0","array":true},{"name":"edge1","array":true},{"name":"x","array":false},{"name":"target","array":true,"type":"double"}]},{"name":"ease","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `t, d, b[bI + ${i}], c[cI + ${i}], easing`; }","args":[{"name":"t","array":false},{"name":"d","array":false},{"name":"b","array":true},{"name":"c","array":true},{"name":"easing","type":"Easing","array":false},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `easing.ease(t, b[bI + ${i}], c[cI + ${i}], d)`"},{"name":"distance","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `p0[p0Off + ${i}], p1[p1Off + ${i}]`; }","args":[{"name":"p0","array":true},{"name":"p1","array":true},{"name":"target","array":true,"type":"double"}],"inline":"_NuFrRa_(i, accept) => `Math.sqrt(Math.pow(p0[p0Off + ${i}], 2) + Math.pow(p1[p1Off + ${i}], 2))`"},{"name":"refract","mod":["public","static"],"ret":"double","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `I[II + ${i}], N[NI + ${i}], eta, dot`; }","args":[{"name":"I","array":true},{"name":"N","array":true},{"name":"eta","array":false},{"name":"target","array":true,"type":"double"}]},{"name":"fold2","mod":["public","static"],"group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `fold[foldI + ${i}]`; }","args":[{"name":"fold","array":true},{"name":"target","array":true}]},{"name":"lessThan","mod":["public","static"],"ret":"boolean","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true,"type":"boolean"}],"inline":"_NuFrRa_(i, accept) => `x[xI + ${i}] < y[yI + ${i}]`"},{"name":"lessThanEqual","mod":["public","static"],"ret":"boolean","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true,"type":"boolean"}],"inline":"_NuFrRa_(i, accept) => `x[xI + ${i}] <= y[yI + ${i}]`"},{"name":"greaterThan","mod":["public","static"],"ret":"boolean","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true,"type":"boolean"}],"inline":"_NuFrRa_(i, accept) => `x[xI + ${i}] > y[yI + ${i}]`"},{"name":"greaterThanEqual","mod":["public","static"],"ret":"boolean","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true,"type":"boolean"}],"inline":"_NuFrRa_(i, accept) => `x[xI + ${i}] >= y[yI + ${i}]`"},{"name":"equal","mod":["public","static"],"ret":"boolean","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true,"type":"boolean"}],"inline":"_NuFrRa_(i, accept) => `x[xI + ${i}] == y[yI + ${i}]`"},{"name":"notEqual","mod":["public","static"],"ret":"boolean","group":4,"additional":true,"accepts":["int","long","short","float","double","char"],"callArgs":"_NuFrRa_(i) => { return `x[xI + ${i}], y[yI + ${i}]`; }","args":[{"name":"x","array":true},{"name":"y","array":true},{"name":"target","array":true,"type":"boolean"}],"inline":"_NuFrRa_(i, accept) => `x[xI + ${i}] != y[yI + ${i}]`"}]';
let methods = JSON.parse(methodsString, (key, value) => {
  if(typeof value != 'string') return value;
  if(value.length < 8) return value;
  let prefix = value.substring(0, 8);
  if(prefix === 'function') return eval('(' + value + ')');
  if(prefix === '_PxEgEr_') return eval(value.slice(8));
  if(prefix === '_NuFrRa_') return eval(value.slice(8));
  return value;
});
(() => {
	for(let method of methods) {
		const result = [];
		const defArgs = [];
		for(let accept of method.accepts)
			result.push(generateMethod(method, accept));
		for(let accept of method.accepts)
			result.push(generateDefault1Method(method, accept));
		for(let accept of method.accepts)
			result.push(generateDefault2Method(method, accept));
		for(let accept of method.accepts)
			defArgs.push(...generateDefault3Method(method, accept));
		for(let i = 0; i < Math.floor(defArgs.length / method.accepts.length); i++)
			for(let j = 0; j < method.accepts.length; j++) result.push(defArgs[i * method.accepts.length + j]);
		for(let accept of method.accepts)
			result.push(generateDefault4Method(method, accept));
		console.log(result.filter(val => !!val).join('\n'));
	}
});
(() => {
	let methodDefinitions = []
	for(let method of methods) {
		const result = [];
		const defArgs = [];
		for(let accept of method.accepts)
			result.push(generateMethod(method, accept));
		for(let accept of method.accepts)
			result.push(generateDefault1Method(method, accept));
		for(let accept of method.accepts)
			result.push(generateDefault2Method(method, accept));
		for(let accept of method.accepts)
			defArgs.push(...generateDefault3Method(method, accept));
		for(let i = 0; i < Math.floor(defArgs.length / method.accepts.length); i++)
			for(let j = 0; j < method.accepts.length; j++) result.push(defArgs[i * method.accepts.length + j]);
		for(let accept of method.accepts)
			result.push(generateDefault4Method(method, accept));
		methodDefinitions.push(...result.filter(val => !!val).map(val => `${val.replaceAll("static ", "")};`));
	} console.log(methodDefinitions.join("\n"));
});
 */

public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {
	private static final LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<>();

	static { try {
//		roman_numerals.put("Mˉ", 1000000);
//		roman_numerals.put("Dˉ", 500000);
//		roman_numerals.put("Cˉ", 100000);
//		roman_numerals.put("Lˉ", 50000);
//		roman_numerals.put("Xˉ", 10000);
//		roman_numerals.put("Vˉ", 5000);
		roman_numerals.put("M", 1000);
		roman_numerals.put("CM", 900);
		roman_numerals.put("D", 500);
		roman_numerals.put("CD", 400);
		roman_numerals.put("C", 100);
		roman_numerals.put("XC", 90);
		roman_numerals.put("L", 50);
		roman_numerals.put("XL", 40);
		roman_numerals.put("X", 10);
		roman_numerals.put("IX", 9);
		roman_numerals.put("V", 5);
		roman_numerals.put("IV", 4);
		roman_numerals.put("I", 1);

		if(SystemUtils.JAVA_DETECTION_VERSION > 8) {
			FNumberUtilsImplementation instance = (FNumberUtilsImplementation) FutureJavaUtils.fastCall("getInstance");
			FMA_INSTANCE = instance.getFMAInstance();
		}
	} catch(Exception e) { throw new RuntimeException(e); } }

	// Map
	public static int map(int x, int inMin, int inMax, int outMin, int outMax) { return (int) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static long map(int x, int inMin, int inMax, long outMin, long outMax) { return (long) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static short map(int x, int inMin, int inMax, short outMin, short outMax) { return (short) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static float map(int x, int inMin, int inMax, float outMin, float outMax) { return (float) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static double map(int x, int inMin, int inMax, double outMin, double outMax) { return (double) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static char map(int x, int inMin, int inMax, char outMin, char outMax) { return (char) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static int map(long x, long inMin, long inMax, int outMin, int outMax) { return (int) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static long map(long x, long inMin, long inMax, long outMin, long outMax) { return (long) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static short map(long x, long inMin, long inMax, short outMin, short outMax) { return (short) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static float map(long x, long inMin, long inMax, float outMin, float outMax) { return (float) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static double map(long x, long inMin, long inMax, double outMin, double outMax) { return (double) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static char map(long x, long inMin, long inMax, char outMin, char outMax) { return (char) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static int map(short x, short inMin, short inMax, int outMin, int outMax) { return (int) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static long map(short x, short inMin, short inMax, long outMin, long outMax) { return (long) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static short map(short x, short inMin, short inMax, short outMin, short outMax) { return (short) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static float map(short x, short inMin, short inMax, float outMin, float outMax) { return (float) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static double map(short x, short inMin, short inMax, double outMin, double outMax) { return (double) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static char map(short x, short inMin, short inMax, char outMin, char outMax) { return (char) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static int map(float x, float inMin, float inMax, int outMin, int outMax) { return (int) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static long map(float x, float inMin, float inMax, long outMin, long outMax) { return (long) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static short map(float x, float inMin, float inMax, short outMin, short outMax) { return (short) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static float map(float x, float inMin, float inMax, float outMin, float outMax) { return (float) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static double map(float x, float inMin, float inMax, double outMin, double outMax) { return (double) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static char map(float x, float inMin, float inMax, char outMin, char outMax) { return (char) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static int map(double x, double inMin, double inMax, int outMin, int outMax) { return (int) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static long map(double x, double inMin, double inMax, long outMin, long outMax) { return (long) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static short map(double x, double inMin, double inMax, short outMin, short outMax) { return (short) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static float map(double x, double inMin, double inMax, float outMin, float outMax) { return (float) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static double map(double x, double inMin, double inMax, double outMin, double outMax) { return (double) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static char map(double x, double inMin, double inMax, char outMin, char outMax) { return (char) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static int map(char x, char inMin, char inMax, int outMin, int outMax) { return (int) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static long map(char x, char inMin, char inMax, long outMin, long outMax) { return (long) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static short map(char x, char inMin, char inMax, short outMin, short outMax) { return (short) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static float map(char x, char inMin, char inMax, float outMin, float outMax) { return (float) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static double map(char x, char inMin, char inMax, double outMin, double outMax) { return (double) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }
	public static char map(char x, char inMin, char inMax, char outMin, char outMax) { return (char) ((x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin); }

	// Random
	public static int rand(int min, int max, Random random) { return map(random.nextDouble(), 0, 1, min, max); }
	public static long rand(long min, long max, Random random) { return map(random.nextDouble(), 0, 1, min, max); }
	public static short rand(short min, short max, Random random) { return map(random.nextDouble(), 0, 1, min, max); }
	public static float rand(float min, float max, Random random) { return map(random.nextDouble(), 0, 1, min, max); }
	public static double rand(double min, double max, Random random) { return map(random.nextDouble(), 0, 1, min, max); }
	public static char rand(char min, char max, Random random) { return map(random.nextDouble(), 0, 1, min, max); }
	public static int rand(int min, int max) { return rand(min, max, PublicRandom.getRandom()); }
	public static long rand(long min, long max) { return rand(min, max, PublicRandom.getRandom()); }
	public static short rand(short min, short max) { return rand(min, max, PublicRandom.getRandom()); }
	public static float rand(float min, float max) { return rand(min, max, PublicRandom.getRandom()); }
	public static double rand(double min, double max) { return rand(min, max, PublicRandom.getRandom()); }
	public static char rand(char min, char max) { return rand(min, max, PublicRandom.getRandom()); }
	public static int[] rand(int min, int max, Random random, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int targetI = targetOff; { for(; i < fit; i += 4, targetI += 4) { target[targetI + 0] = (int) (random.nextDouble() * (max - min) + min); target[targetI + 1] = (int) (random.nextDouble() * (max - min) + min); target[targetI + 2] = (int) (random.nextDouble() * (max - min) + min); target[targetI + 3] = (int) (random.nextDouble() * (max - min) + min); } for(; i < len; i++, targetI++) target[targetI + 0] = (int) (random.nextDouble() * (max - min) + min); } return target; }
	public static long[] rand(long min, long max, Random random, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int targetI = targetOff; { for(; i < fit; i += 4, targetI += 4) { target[targetI + 0] = (long) (random.nextDouble() * (max - min) + min); target[targetI + 1] = (long) (random.nextDouble() * (max - min) + min); target[targetI + 2] = (long) (random.nextDouble() * (max - min) + min); target[targetI + 3] = (long) (random.nextDouble() * (max - min) + min); } for(; i < len; i++, targetI++) target[targetI + 0] = (long) (random.nextDouble() * (max - min) + min); } return target; }
	public static short[] rand(short min, short max, Random random, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int targetI = targetOff; { for(; i < fit; i += 4, targetI += 4) { target[targetI + 0] = (short) (random.nextDouble() * (max - min) + min); target[targetI + 1] = (short) (random.nextDouble() * (max - min) + min); target[targetI + 2] = (short) (random.nextDouble() * (max - min) + min); target[targetI + 3] = (short) (random.nextDouble() * (max - min) + min); } for(; i < len; i++, targetI++) target[targetI + 0] = (short) (random.nextDouble() * (max - min) + min); } return target; }
	public static float[] rand(float min, float max, Random random, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int targetI = targetOff; { for(; i < fit; i += 4, targetI += 4) { target[targetI + 0] = (float) (random.nextDouble() * (max - min) + min); target[targetI + 1] = (float) (random.nextDouble() * (max - min) + min); target[targetI + 2] = (float) (random.nextDouble() * (max - min) + min); target[targetI + 3] = (float) (random.nextDouble() * (max - min) + min); } for(; i < len; i++, targetI++) target[targetI + 0] = (float) (random.nextDouble() * (max - min) + min); } return target; }
	public static double[] rand(double min, double max, Random random, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int targetI = targetOff; { for(; i < fit; i += 4, targetI += 4) { target[targetI + 0] = (double) (random.nextDouble() * (max - min) + min); target[targetI + 1] = (double) (random.nextDouble() * (max - min) + min); target[targetI + 2] = (double) (random.nextDouble() * (max - min) + min); target[targetI + 3] = (double) (random.nextDouble() * (max - min) + min); } for(; i < len; i++, targetI++) target[targetI + 0] = (double) (random.nextDouble() * (max - min) + min); } return target; }
	public static char[] rand(char min, char max, Random random, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int targetI = targetOff; { for(; i < fit; i += 4, targetI += 4) { target[targetI + 0] = (char) (random.nextDouble() * (max - min) + min); target[targetI + 1] = (char) (random.nextDouble() * (max - min) + min); target[targetI + 2] = (char) (random.nextDouble() * (max - min) + min); target[targetI + 3] = (char) (random.nextDouble() * (max - min) + min); } for(; i < len; i++, targetI++) target[targetI + 0] = (char) (random.nextDouble() * (max - min) + min); } return target; }
	public static int[] rand(int min, int max, Random random, int[] target, int targetOff) { return rand(min, max, random, target, targetOff, target.length - targetOff); }
	public static long[] rand(long min, long max, Random random, long[] target, int targetOff) { return rand(min, max, random, target, targetOff, target.length - targetOff); }
	public static short[] rand(short min, short max, Random random, short[] target, int targetOff) { return rand(min, max, random, target, targetOff, target.length - targetOff); }
	public static float[] rand(float min, float max, Random random, float[] target, int targetOff) { return rand(min, max, random, target, targetOff, target.length - targetOff); }
	public static double[] rand(double min, double max, Random random, double[] target, int targetOff) { return rand(min, max, random, target, targetOff, target.length - targetOff); }
	public static char[] rand(char min, char max, Random random, char[] target, int targetOff) { return rand(min, max, random, target, targetOff, target.length - targetOff); }
	public static int[] rand(int min, int max, Random random, int[] target) { return rand(min, max, random, target, 0); }
	public static long[] rand(long min, long max, Random random, long[] target) { return rand(min, max, random, target, 0); }
	public static short[] rand(short min, short max, Random random, short[] target) { return rand(min, max, random, target, 0); }
	public static float[] rand(float min, float max, Random random, float[] target) { return rand(min, max, random, target, 0); }
	public static double[] rand(double min, double max, Random random, double[] target) { return rand(min, max, random, target, 0); }
	public static char[] rand(char min, char max, Random random, char[] target) { return rand(min, max, random, target, 0); }
	public static int[] rand(int min, int max, int[] target) { return rand(min, max, PublicRandom.getRandom(), target); }
	public static long[] rand(long min, long max, long[] target) { return rand(min, max, PublicRandom.getRandom(), target); }
	public static short[] rand(short min, short max, short[] target) { return rand(min, max, PublicRandom.getRandom(), target); }
	public static float[] rand(float min, float max, float[] target) { return rand(min, max, PublicRandom.getRandom(), target); }
	public static double[] rand(double min, double max, double[] target) { return rand(min, max, PublicRandom.getRandom(), target); }
	public static char[] rand(char min, char max, char[] target) { return rand(min, max, PublicRandom.getRandom(), target); }
	public static int randInt() { return rand(Integer.MIN_VALUE, Integer.MAX_VALUE); }
	public static long randLong() { return rand(Long.MIN_VALUE, Long.MAX_VALUE); }
	public static short randShort() { return rand(Short.MIN_VALUE, Short.MAX_VALUE); }
	public static float randFloat() { return rand(Float.MIN_VALUE, Float.MAX_VALUE); }
	public static double randDouble() { return rand(Double.MIN_VALUE, Double.MAX_VALUE); }
	public static char randChar() { return rand(Character.MIN_VALUE, Character.MAX_VALUE); }

	// fused mul–add
	public static final boolean USE_FMA = Boolean.parseBoolean(System.getProperty("numberutils.usefma", SystemUtils.JAVA_DETECTION_VERSION > 8 ? "true" : "false"));
	public static interface FMA_CALLBACK { double fma(double a, double b, double c); }
	public static FMA_CALLBACK FMA_INSTANCE;
	public static double fma(double a, double b, double c) { return USE_FMA && FMA_INSTANCE != null ? FMA_INSTANCE.fma(a, b, c) : a * b + c; }
	public static double fma(double a, double b) { return fma(a, b, 0); }
	public static float[] fma(float[] a, int aOff, float[] b, int bOff, float[] c, int cOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(aOff, a.length, len); ArrayUtils.assertIndex(bOff, b.length, len); ArrayUtils.assertIndex(cOff, c.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int aI = aOff; int bI = bOff; int cI = cOff; int targetI = targetOff; if(USE_FMA && FMA_INSTANCE != null) { for(; i < fit; i += 4, aI += 4, bI += 4, cI += 4, targetI += 4) { target[targetI + 0] = (float) (FMA_INSTANCE.fma(a[aI + 0], b[bI + 0], c[cI + 0])); target[targetI + 1] = (float) (FMA_INSTANCE.fma(a[aI + 1], b[bI + 1], c[cI + 1])); target[targetI + 2] = (float) (FMA_INSTANCE.fma(a[aI + 2], b[bI + 2], c[cI + 2])); target[targetI + 3] = (float) (FMA_INSTANCE.fma(a[aI + 3], b[bI + 3], c[cI + 3])); } for(; i < len; i++, aI++, bI++, cI++, targetI++) target[targetI + 0] = (float) (FMA_INSTANCE.fma(a[aI + 0], b[bI + 0], c[cI + 0])); } else { for(; i < fit; i += 4, aI += 4, bI += 4, cI += 4, targetI += 4) { target[targetI + 0] = (float) (a[aI + 0] * b[bI + 0] + c[cI + 0]); target[targetI + 1] = (float) (a[aI + 1] * b[bI + 1] + c[cI + 1]); target[targetI + 2] = (float) (a[aI + 2] * b[bI + 2] + c[cI + 2]); target[targetI + 3] = (float) (a[aI + 3] * b[bI + 3] + c[cI + 3]); } for(; i < len; i++, aI++, bI++, cI++, targetI++) target[targetI + 0] = (float) (a[aI + 0] * b[bI + 0] + c[cI + 0]); } return target; }
	public static double[] fma(double[] a, int aOff, double[] b, int bOff, double[] c, int cOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(aOff, a.length, len); ArrayUtils.assertIndex(bOff, b.length, len); ArrayUtils.assertIndex(cOff, c.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int aI = aOff; int bI = bOff; int cI = cOff; int targetI = targetOff; if(USE_FMA && FMA_INSTANCE != null) { for(; i < fit; i += 4, aI += 4, bI += 4, cI += 4, targetI += 4) { target[targetI + 0] = (double) (FMA_INSTANCE.fma(a[aI + 0], b[bI + 0], c[cI + 0])); target[targetI + 1] = (double) (FMA_INSTANCE.fma(a[aI + 1], b[bI + 1], c[cI + 1])); target[targetI + 2] = (double) (FMA_INSTANCE.fma(a[aI + 2], b[bI + 2], c[cI + 2])); target[targetI + 3] = (double) (FMA_INSTANCE.fma(a[aI + 3], b[bI + 3], c[cI + 3])); } for(; i < len; i++, aI++, bI++, cI++, targetI++) target[targetI + 0] = (double) (FMA_INSTANCE.fma(a[aI + 0], b[bI + 0], c[cI + 0])); } else { for(; i < fit; i += 4, aI += 4, bI += 4, cI += 4, targetI += 4) { target[targetI + 0] = (double) (a[aI + 0] * b[bI + 0] + c[cI + 0]); target[targetI + 1] = (double) (a[aI + 1] * b[bI + 1] + c[cI + 1]); target[targetI + 2] = (double) (a[aI + 2] * b[bI + 2] + c[cI + 2]); target[targetI + 3] = (double) (a[aI + 3] * b[bI + 3] + c[cI + 3]); } for(; i < len; i++, aI++, bI++, cI++, targetI++) target[targetI + 0] = (double) (a[aI + 0] * b[bI + 0] + c[cI + 0]); } return target; }
	public static float[] fma(float[] a, int aOff, float[] b, int bOff, float[] c, int cOff, float[] target, int targetOff) { return fma(a, aOff, b, bOff, c, cOff, target, targetOff, target.length - targetOff); }
	public static double[] fma(double[] a, int aOff, double[] b, int bOff, double[] c, int cOff, double[] target, int targetOff) { return fma(a, aOff, b, bOff, c, cOff, target, targetOff, target.length - targetOff); }
	public static float[] fma(float[] a, float[] b, float[] c, float[] target) { return fma(a, 0, b, 0, c, 0, target, 0); }
	public static double[] fma(double[] a, double[] b, double[] c, double[] target) { return fma(a, 0, b, 0, c, 0, target, 0); }
	public static float[] _fma(float[] a, float[] b, float[] c) { return fma(a, b, c, new float[(a.length + b.length + c.length) / 3]); }
	public static double[] _fma(double[] a, double[] b, double[] c) { return fma(a, b, c, new double[(a.length + b.length + c.length) / 3]); }
	public static float[] fma(float[] a, int aOff, float[] b, int bOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(aOff, a.length, len); ArrayUtils.assertIndex(bOff, b.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int aI = aOff; int bI = bOff; int targetI = targetOff; if(USE_FMA && FMA_INSTANCE != null) { for(; i < fit; i += 4, aI += 4, bI += 4, targetI += 4) { target[targetI + 0] = (float) (FMA_INSTANCE.fma(a[aI + 0], b[bI + 0], 0)); target[targetI + 1] = (float) (FMA_INSTANCE.fma(a[aI + 1], b[bI + 1], 0)); target[targetI + 2] = (float) (FMA_INSTANCE.fma(a[aI + 2], b[bI + 2], 0)); target[targetI + 3] = (float) (FMA_INSTANCE.fma(a[aI + 3], b[bI + 3], 0)); } for(; i < len; i++, aI++, bI++, targetI++) target[targetI + 0] = (float) (FMA_INSTANCE.fma(a[aI + 0], b[bI + 0], 0)); } else { for(; i < fit; i += 4, aI += 4, bI += 4, targetI += 4) { target[targetI + 0] = (float) (a[aI + 0] * b[bI + 0]); target[targetI + 1] = (float) (a[aI + 1] * b[bI + 1]); target[targetI + 2] = (float) (a[aI + 2] * b[bI + 2]); target[targetI + 3] = (float) (a[aI + 3] * b[bI + 3]); } for(; i < len; i++, aI++, bI++, targetI++) target[targetI + 0] = (float) (a[aI + 0] * b[bI + 0]); } return target; }
	public static double[] fma(double[] a, int aOff, double[] b, int bOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(aOff, a.length, len); ArrayUtils.assertIndex(bOff, b.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int aI = aOff; int bI = bOff; int targetI = targetOff; if(USE_FMA && FMA_INSTANCE != null) { for(; i < fit; i += 4, aI += 4, bI += 4, targetI += 4) { target[targetI + 0] = (double) (FMA_INSTANCE.fma(a[aI + 0], b[bI + 0], 0)); target[targetI + 1] = (double) (FMA_INSTANCE.fma(a[aI + 1], b[bI + 1], 0)); target[targetI + 2] = (double) (FMA_INSTANCE.fma(a[aI + 2], b[bI + 2], 0)); target[targetI + 3] = (double) (FMA_INSTANCE.fma(a[aI + 3], b[bI + 3], 0)); } for(; i < len; i++, aI++, bI++, targetI++) target[targetI + 0] = (double) (FMA_INSTANCE.fma(a[aI + 0], b[bI + 0], 0)); } else { for(; i < fit; i += 4, aI += 4, bI += 4, targetI += 4) { target[targetI + 0] = (double) (a[aI + 0] * b[bI + 0]); target[targetI + 1] = (double) (a[aI + 1] * b[bI + 1]); target[targetI + 2] = (double) (a[aI + 2] * b[bI + 2]); target[targetI + 3] = (double) (a[aI + 3] * b[bI + 3]); } for(; i < len; i++, aI++, bI++, targetI++) target[targetI + 0] = (double) (a[aI + 0] * b[bI + 0]); } return target; }
	public static float[] fma(float[] a, int aOff, float[] b, int bOff, float[] target, int targetOff) { return fma(a, aOff, b, bOff, target, targetOff, target.length - targetOff); }
	public static double[] fma(double[] a, int aOff, double[] b, int bOff, double[] target, int targetOff) { return fma(a, aOff, b, bOff, target, targetOff, target.length - targetOff); }
	public static float[] fma(float[] a, float[] b, float[] target) { return fma(a, 0, b, 0, target, 0); }
	public static double[] fma(double[] a, double[] b, double[] target) { return fma(a, 0, b, 0, target, 0); }
	public static float[] _fma(float[] a, float[] b) { return fma(a, b, new float[(a.length + b.length) / 2]); }
	public static double[] _fma(double[] a, double[] b) { return fma(a, b, new double[(a.length + b.length) / 2]); }

	// radians
	public static double radians(double degrees) { return Math.toRadians(degrees); }
	public static double[] radians(int[] degrees, int degreesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(degreesOff, degrees.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int degreesI = degreesOff; int targetI = targetOff; { for(; i < fit; i += 4, degreesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); target[targetI + 1] = (double) (Math.toRadians(degrees[degreesI + 1])); target[targetI + 2] = (double) (Math.toRadians(degrees[degreesI + 2])); target[targetI + 3] = (double) (Math.toRadians(degrees[degreesI + 3])); } for(; i < len; i++, degreesI++, targetI++) target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); } return target; }
	public static double[] radians(long[] degrees, int degreesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(degreesOff, degrees.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int degreesI = degreesOff; int targetI = targetOff; { for(; i < fit; i += 4, degreesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); target[targetI + 1] = (double) (Math.toRadians(degrees[degreesI + 1])); target[targetI + 2] = (double) (Math.toRadians(degrees[degreesI + 2])); target[targetI + 3] = (double) (Math.toRadians(degrees[degreesI + 3])); } for(; i < len; i++, degreesI++, targetI++) target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); } return target; }
	public static double[] radians(short[] degrees, int degreesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(degreesOff, degrees.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int degreesI = degreesOff; int targetI = targetOff; { for(; i < fit; i += 4, degreesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); target[targetI + 1] = (double) (Math.toRadians(degrees[degreesI + 1])); target[targetI + 2] = (double) (Math.toRadians(degrees[degreesI + 2])); target[targetI + 3] = (double) (Math.toRadians(degrees[degreesI + 3])); } for(; i < len; i++, degreesI++, targetI++) target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); } return target; }
	public static double[] radians(float[] degrees, int degreesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(degreesOff, degrees.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int degreesI = degreesOff; int targetI = targetOff; { for(; i < fit; i += 4, degreesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); target[targetI + 1] = (double) (Math.toRadians(degrees[degreesI + 1])); target[targetI + 2] = (double) (Math.toRadians(degrees[degreesI + 2])); target[targetI + 3] = (double) (Math.toRadians(degrees[degreesI + 3])); } for(; i < len; i++, degreesI++, targetI++) target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); } return target; }
	public static double[] radians(double[] degrees, int degreesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(degreesOff, degrees.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int degreesI = degreesOff; int targetI = targetOff; { for(; i < fit; i += 4, degreesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); target[targetI + 1] = (double) (Math.toRadians(degrees[degreesI + 1])); target[targetI + 2] = (double) (Math.toRadians(degrees[degreesI + 2])); target[targetI + 3] = (double) (Math.toRadians(degrees[degreesI + 3])); } for(; i < len; i++, degreesI++, targetI++) target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); } return target; }
	public static double[] radians(char[] degrees, int degreesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(degreesOff, degrees.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int degreesI = degreesOff; int targetI = targetOff; { for(; i < fit; i += 4, degreesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); target[targetI + 1] = (double) (Math.toRadians(degrees[degreesI + 1])); target[targetI + 2] = (double) (Math.toRadians(degrees[degreesI + 2])); target[targetI + 3] = (double) (Math.toRadians(degrees[degreesI + 3])); } for(; i < len; i++, degreesI++, targetI++) target[targetI + 0] = (double) (Math.toRadians(degrees[degreesI + 0])); } return target; }
	public static double[] radians(int[] degrees, int degreesOff, double[] target, int targetOff) { return radians(degrees, degreesOff, target, targetOff, target.length - targetOff); }
	public static double[] radians(long[] degrees, int degreesOff, double[] target, int targetOff) { return radians(degrees, degreesOff, target, targetOff, target.length - targetOff); }
	public static double[] radians(short[] degrees, int degreesOff, double[] target, int targetOff) { return radians(degrees, degreesOff, target, targetOff, target.length - targetOff); }
	public static double[] radians(float[] degrees, int degreesOff, double[] target, int targetOff) { return radians(degrees, degreesOff, target, targetOff, target.length - targetOff); }
	public static double[] radians(double[] degrees, int degreesOff, double[] target, int targetOff) { return radians(degrees, degreesOff, target, targetOff, target.length - targetOff); }
	public static double[] radians(char[] degrees, int degreesOff, double[] target, int targetOff) { return radians(degrees, degreesOff, target, targetOff, target.length - targetOff); }
	public static double[] radians(int[] degrees, double[] target) { return radians(degrees, 0, target, 0); }
	public static double[] radians(long[] degrees, double[] target) { return radians(degrees, 0, target, 0); }
	public static double[] radians(short[] degrees, double[] target) { return radians(degrees, 0, target, 0); }
	public static double[] radians(float[] degrees, double[] target) { return radians(degrees, 0, target, 0); }
	public static double[] radians(double[] degrees, double[] target) { return radians(degrees, 0, target, 0); }
	public static double[] radians(char[] degrees, double[] target) { return radians(degrees, 0, target, 0); }
	public static double[] radians(int[] degrees) { return radians(degrees, new double[(degrees.length) / 1]); }
	public static double[] radians(long[] degrees) { return radians(degrees, new double[(degrees.length) / 1]); }
	public static double[] radians(short[] degrees) { return radians(degrees, new double[(degrees.length) / 1]); }
	public static double[] radians(float[] degrees) { return radians(degrees, new double[(degrees.length) / 1]); }
	public static double[] radians(double[] degrees) { return radians(degrees, new double[(degrees.length) / 1]); }
	public static double[] radians(char[] degrees) { return radians(degrees, new double[(degrees.length) / 1]); }

	// degress
	public static double degrees(double radians) { return Math.toDegrees(radians); }
	public static double[] degrees(int[] radians, int radiansOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(radiansOff, radians.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int radiansI = radiansOff; int targetI = targetOff; { for(; i < fit; i += 4, radiansI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); target[targetI + 1] = (double) (Math.toDegrees(radians[radiansI + 1])); target[targetI + 2] = (double) (Math.toDegrees(radians[radiansI + 2])); target[targetI + 3] = (double) (Math.toDegrees(radians[radiansI + 3])); } for(; i < len; i++, radiansI++, targetI++) target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); } return target; }
	public static double[] degrees(long[] radians, int radiansOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(radiansOff, radians.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int radiansI = radiansOff; int targetI = targetOff; { for(; i < fit; i += 4, radiansI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); target[targetI + 1] = (double) (Math.toDegrees(radians[radiansI + 1])); target[targetI + 2] = (double) (Math.toDegrees(radians[radiansI + 2])); target[targetI + 3] = (double) (Math.toDegrees(radians[radiansI + 3])); } for(; i < len; i++, radiansI++, targetI++) target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); } return target; }
	public static double[] degrees(short[] radians, int radiansOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(radiansOff, radians.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int radiansI = radiansOff; int targetI = targetOff; { for(; i < fit; i += 4, radiansI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); target[targetI + 1] = (double) (Math.toDegrees(radians[radiansI + 1])); target[targetI + 2] = (double) (Math.toDegrees(radians[radiansI + 2])); target[targetI + 3] = (double) (Math.toDegrees(radians[radiansI + 3])); } for(; i < len; i++, radiansI++, targetI++) target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); } return target; }
	public static double[] degrees(float[] radians, int radiansOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(radiansOff, radians.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int radiansI = radiansOff; int targetI = targetOff; { for(; i < fit; i += 4, radiansI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); target[targetI + 1] = (double) (Math.toDegrees(radians[radiansI + 1])); target[targetI + 2] = (double) (Math.toDegrees(radians[radiansI + 2])); target[targetI + 3] = (double) (Math.toDegrees(radians[radiansI + 3])); } for(; i < len; i++, radiansI++, targetI++) target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); } return target; }
	public static double[] degrees(double[] radians, int radiansOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(radiansOff, radians.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int radiansI = radiansOff; int targetI = targetOff; { for(; i < fit; i += 4, radiansI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); target[targetI + 1] = (double) (Math.toDegrees(radians[radiansI + 1])); target[targetI + 2] = (double) (Math.toDegrees(radians[radiansI + 2])); target[targetI + 3] = (double) (Math.toDegrees(radians[radiansI + 3])); } for(; i < len; i++, radiansI++, targetI++) target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); } return target; }
	public static double[] degrees(char[] radians, int radiansOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(radiansOff, radians.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int radiansI = radiansOff; int targetI = targetOff; { for(; i < fit; i += 4, radiansI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); target[targetI + 1] = (double) (Math.toDegrees(radians[radiansI + 1])); target[targetI + 2] = (double) (Math.toDegrees(radians[radiansI + 2])); target[targetI + 3] = (double) (Math.toDegrees(radians[radiansI + 3])); } for(; i < len; i++, radiansI++, targetI++) target[targetI + 0] = (double) (Math.toDegrees(radians[radiansI + 0])); } return target; }
	public static double[] degrees(int[] radians, int radiansOff, double[] target, int targetOff) { return degrees(radians, radiansOff, target, targetOff, target.length - targetOff); }
	public static double[] degrees(long[] radians, int radiansOff, double[] target, int targetOff) { return degrees(radians, radiansOff, target, targetOff, target.length - targetOff); }
	public static double[] degrees(short[] radians, int radiansOff, double[] target, int targetOff) { return degrees(radians, radiansOff, target, targetOff, target.length - targetOff); }
	public static double[] degrees(float[] radians, int radiansOff, double[] target, int targetOff) { return degrees(radians, radiansOff, target, targetOff, target.length - targetOff); }
	public static double[] degrees(double[] radians, int radiansOff, double[] target, int targetOff) { return degrees(radians, radiansOff, target, targetOff, target.length - targetOff); }
	public static double[] degrees(char[] radians, int radiansOff, double[] target, int targetOff) { return degrees(radians, radiansOff, target, targetOff, target.length - targetOff); }
	public static double[] degrees(int[] radians, double[] target) { return degrees(radians, 0, target, 0); }
	public static double[] degrees(long[] radians, double[] target) { return degrees(radians, 0, target, 0); }
	public static double[] degrees(short[] radians, double[] target) { return degrees(radians, 0, target, 0); }
	public static double[] degrees(float[] radians, double[] target) { return degrees(radians, 0, target, 0); }
	public static double[] degrees(double[] radians, double[] target) { return degrees(radians, 0, target, 0); }
	public static double[] degrees(char[] radians, double[] target) { return degrees(radians, 0, target, 0); }
	public static double[] degrees(int[] radians) { return degrees(radians, new double[(radians.length) / 1]); }
	public static double[] degrees(long[] radians) { return degrees(radians, new double[(radians.length) / 1]); }
	public static double[] degrees(short[] radians) { return degrees(radians, new double[(radians.length) / 1]); }
	public static double[] degrees(float[] radians) { return degrees(radians, new double[(radians.length) / 1]); }
	public static double[] degrees(double[] radians) { return degrees(radians, new double[(radians.length) / 1]); }
	public static double[] degrees(char[] radians) { return degrees(radians, new double[(radians.length) / 1]); }

	// sin
	public static double sin(double angle) { return Math.sin(angle); }
	public static double[] sin(int[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.sin(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.sin(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.sin(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); } return target; }
	public static double[] sin(long[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.sin(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.sin(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.sin(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); } return target; }
	public static double[] sin(short[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.sin(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.sin(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.sin(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); } return target; }
	public static double[] sin(float[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.sin(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.sin(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.sin(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); } return target; }
	public static double[] sin(double[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.sin(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.sin(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.sin(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); } return target; }
	public static double[] sin(char[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.sin(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.sin(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.sin(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.sin(angles[anglesI + 0])); } return target; }
	public static double[] sin(int[] angles, int anglesOff, double[] target, int targetOff) { return sin(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] sin(long[] angles, int anglesOff, double[] target, int targetOff) { return sin(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] sin(short[] angles, int anglesOff, double[] target, int targetOff) { return sin(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] sin(float[] angles, int anglesOff, double[] target, int targetOff) { return sin(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] sin(double[] angles, int anglesOff, double[] target, int targetOff) { return sin(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] sin(char[] angles, int anglesOff, double[] target, int targetOff) { return sin(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] sin(int[] angles, double[] target) { return sin(angles, 0, target, 0); }
	public static double[] sin(long[] angles, double[] target) { return sin(angles, 0, target, 0); }
	public static double[] sin(short[] angles, double[] target) { return sin(angles, 0, target, 0); }
	public static double[] sin(float[] angles, double[] target) { return sin(angles, 0, target, 0); }
	public static double[] sin(double[] angles, double[] target) { return sin(angles, 0, target, 0); }
	public static double[] sin(char[] angles, double[] target) { return sin(angles, 0, target, 0); }
	public static double[] sin(int[] angles) { return sin(angles, new double[(angles.length) / 1]); }
	public static double[] sin(long[] angles) { return sin(angles, new double[(angles.length) / 1]); }
	public static double[] sin(short[] angles) { return sin(angles, new double[(angles.length) / 1]); }
	public static double[] sin(float[] angles) { return sin(angles, new double[(angles.length) / 1]); }
	public static double[] sin(double[] angles) { return sin(angles, new double[(angles.length) / 1]); }
	public static double[] sin(char[] angles) { return sin(angles, new double[(angles.length) / 1]); }

	// cos
	public static double cos(double angle) { return Math.cos(angle); }
	public static double[] cos(int[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.cos(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.cos(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.cos(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); } return target; }
	public static double[] cos(long[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.cos(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.cos(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.cos(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); } return target; }
	public static double[] cos(short[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.cos(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.cos(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.cos(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); } return target; }
	public static double[] cos(float[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.cos(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.cos(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.cos(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); } return target; }
	public static double[] cos(double[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.cos(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.cos(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.cos(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); } return target; }
	public static double[] cos(char[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.cos(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.cos(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.cos(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.cos(angles[anglesI + 0])); } return target; }
	public static double[] cos(int[] angles, int anglesOff, double[] target, int targetOff) { return cos(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] cos(long[] angles, int anglesOff, double[] target, int targetOff) { return cos(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] cos(short[] angles, int anglesOff, double[] target, int targetOff) { return cos(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] cos(float[] angles, int anglesOff, double[] target, int targetOff) { return cos(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] cos(double[] angles, int anglesOff, double[] target, int targetOff) { return cos(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] cos(char[] angles, int anglesOff, double[] target, int targetOff) { return cos(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] cos(int[] angles, double[] target) { return cos(angles, 0, target, 0); }
	public static double[] cos(long[] angles, double[] target) { return cos(angles, 0, target, 0); }
	public static double[] cos(short[] angles, double[] target) { return cos(angles, 0, target, 0); }
	public static double[] cos(float[] angles, double[] target) { return cos(angles, 0, target, 0); }
	public static double[] cos(double[] angles, double[] target) { return cos(angles, 0, target, 0); }
	public static double[] cos(char[] angles, double[] target) { return cos(angles, 0, target, 0); }
	public static double[] cos(int[] angles) { return cos(angles, new double[(angles.length) / 1]); }
	public static double[] cos(long[] angles) { return cos(angles, new double[(angles.length) / 1]); }
	public static double[] cos(short[] angles) { return cos(angles, new double[(angles.length) / 1]); }
	public static double[] cos(float[] angles) { return cos(angles, new double[(angles.length) / 1]); }
	public static double[] cos(double[] angles) { return cos(angles, new double[(angles.length) / 1]); }
	public static double[] cos(char[] angles) { return cos(angles, new double[(angles.length) / 1]); }

	// tan
	public static double tan(double angle) { return Math.tan(angle); }
	public static double[] tan(int[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.tan(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.tan(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.tan(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); } return target; }
	public static double[] tan(long[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.tan(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.tan(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.tan(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); } return target; }
	public static double[] tan(short[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.tan(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.tan(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.tan(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); } return target; }
	public static double[] tan(float[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.tan(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.tan(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.tan(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); } return target; }
	public static double[] tan(double[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.tan(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.tan(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.tan(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); } return target; }
	public static double[] tan(char[] angles, int anglesOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(anglesOff, angles.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int anglesI = anglesOff; int targetI = targetOff; { for(; i < fit; i += 4, anglesI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); target[targetI + 1] = (double) (Math.tan(angles[anglesI + 1])); target[targetI + 2] = (double) (Math.tan(angles[anglesI + 2])); target[targetI + 3] = (double) (Math.tan(angles[anglesI + 3])); } for(; i < len; i++, anglesI++, targetI++) target[targetI + 0] = (double) (Math.tan(angles[anglesI + 0])); } return target; }
	public static double[] tan(int[] angles, int anglesOff, double[] target, int targetOff) { return tan(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] tan(long[] angles, int anglesOff, double[] target, int targetOff) { return tan(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] tan(short[] angles, int anglesOff, double[] target, int targetOff) { return tan(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] tan(float[] angles, int anglesOff, double[] target, int targetOff) { return tan(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] tan(double[] angles, int anglesOff, double[] target, int targetOff) { return tan(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] tan(char[] angles, int anglesOff, double[] target, int targetOff) { return tan(angles, anglesOff, target, targetOff, target.length - targetOff); }
	public static double[] tan(int[] angles, double[] target) { return tan(angles, 0, target, 0); }
	public static double[] tan(long[] angles, double[] target) { return tan(angles, 0, target, 0); }
	public static double[] tan(short[] angles, double[] target) { return tan(angles, 0, target, 0); }
	public static double[] tan(float[] angles, double[] target) { return tan(angles, 0, target, 0); }
	public static double[] tan(double[] angles, double[] target) { return tan(angles, 0, target, 0); }
	public static double[] tan(char[] angles, double[] target) { return tan(angles, 0, target, 0); }
	public static double[] tan(int[] angles) { return tan(angles, new double[(angles.length) / 1]); }
	public static double[] tan(long[] angles) { return tan(angles, new double[(angles.length) / 1]); }
	public static double[] tan(short[] angles) { return tan(angles, new double[(angles.length) / 1]); }
	public static double[] tan(float[] angles) { return tan(angles, new double[(angles.length) / 1]); }
	public static double[] tan(double[] angles) { return tan(angles, new double[(angles.length) / 1]); }
	public static double[] tan(char[] angles) { return tan(angles, new double[(angles.length) / 1]); }

	// arcsin
	public static double asin(double angle) { return Math.asin(angle); }
	public static double[] asin(int[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.asin(y[yI + 0])); target[targetI + 1] = (double) (Math.asin(y[yI + 1])); target[targetI + 2] = (double) (Math.asin(y[yI + 2])); target[targetI + 3] = (double) (Math.asin(y[yI + 3])); } for(; i < len; i++, yI++, targetI++) target[targetI + 0] = (double) (Math.asin(y[yI + 0])); } return target; }
	public static double[] asin(long[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.asin(y[yI + 0])); target[targetI + 1] = (double) (Math.asin(y[yI + 1])); target[targetI + 2] = (double) (Math.asin(y[yI + 2])); target[targetI + 3] = (double) (Math.asin(y[yI + 3])); } for(; i < len; i++, yI++, targetI++) target[targetI + 0] = (double) (Math.asin(y[yI + 0])); } return target; }
	public static double[] asin(short[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.asin(y[yI + 0])); target[targetI + 1] = (double) (Math.asin(y[yI + 1])); target[targetI + 2] = (double) (Math.asin(y[yI + 2])); target[targetI + 3] = (double) (Math.asin(y[yI + 3])); } for(; i < len; i++, yI++, targetI++) target[targetI + 0] = (double) (Math.asin(y[yI + 0])); } return target; }
	public static double[] asin(float[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.asin(y[yI + 0])); target[targetI + 1] = (double) (Math.asin(y[yI + 1])); target[targetI + 2] = (double) (Math.asin(y[yI + 2])); target[targetI + 3] = (double) (Math.asin(y[yI + 3])); } for(; i < len; i++, yI++, targetI++) target[targetI + 0] = (double) (Math.asin(y[yI + 0])); } return target; }
	public static double[] asin(double[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.asin(y[yI + 0])); target[targetI + 1] = (double) (Math.asin(y[yI + 1])); target[targetI + 2] = (double) (Math.asin(y[yI + 2])); target[targetI + 3] = (double) (Math.asin(y[yI + 3])); } for(; i < len; i++, yI++, targetI++) target[targetI + 0] = (double) (Math.asin(y[yI + 0])); } return target; }
	public static double[] asin(char[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.asin(y[yI + 0])); target[targetI + 1] = (double) (Math.asin(y[yI + 1])); target[targetI + 2] = (double) (Math.asin(y[yI + 2])); target[targetI + 3] = (double) (Math.asin(y[yI + 3])); } for(; i < len; i++, yI++, targetI++) target[targetI + 0] = (double) (Math.asin(y[yI + 0])); } return target; }
	public static double[] asin(int[] y, int yOff, double[] target, int targetOff) { return asin(y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] asin(long[] y, int yOff, double[] target, int targetOff) { return asin(y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] asin(short[] y, int yOff, double[] target, int targetOff) { return asin(y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] asin(float[] y, int yOff, double[] target, int targetOff) { return asin(y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] asin(double[] y, int yOff, double[] target, int targetOff) { return asin(y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] asin(char[] y, int yOff, double[] target, int targetOff) { return asin(y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] asin(int[] y, double[] target) { return asin(y, 0, target, 0); }
	public static double[] asin(long[] y, double[] target) { return asin(y, 0, target, 0); }
	public static double[] asin(short[] y, double[] target) { return asin(y, 0, target, 0); }
	public static double[] asin(float[] y, double[] target) { return asin(y, 0, target, 0); }
	public static double[] asin(double[] y, double[] target) { return asin(y, 0, target, 0); }
	public static double[] asin(char[] y, double[] target) { return asin(y, 0, target, 0); }
	public static double[] asin(int[] y) { return asin(y, new double[(y.length) / 1]); }
	public static double[] asin(long[] y) { return asin(y, new double[(y.length) / 1]); }
	public static double[] asin(short[] y) { return asin(y, new double[(y.length) / 1]); }
	public static double[] asin(float[] y) { return asin(y, new double[(y.length) / 1]); }
	public static double[] asin(double[] y) { return asin(y, new double[(y.length) / 1]); }
	public static double[] asin(char[] y) { return asin(y, new double[(y.length) / 1]); }

	// arccos
	public static double acos(double angle) { return Math.acos(angle); }
	public static double[] acos(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.acos(x[xI + 0])); target[targetI + 1] = (double) (Math.acos(x[xI + 1])); target[targetI + 2] = (double) (Math.acos(x[xI + 2])); target[targetI + 3] = (double) (Math.acos(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.acos(x[xI + 0])); } return target; }
	public static double[] acos(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.acos(x[xI + 0])); target[targetI + 1] = (double) (Math.acos(x[xI + 1])); target[targetI + 2] = (double) (Math.acos(x[xI + 2])); target[targetI + 3] = (double) (Math.acos(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.acos(x[xI + 0])); } return target; }
	public static double[] acos(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.acos(x[xI + 0])); target[targetI + 1] = (double) (Math.acos(x[xI + 1])); target[targetI + 2] = (double) (Math.acos(x[xI + 2])); target[targetI + 3] = (double) (Math.acos(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.acos(x[xI + 0])); } return target; }
	public static double[] acos(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.acos(x[xI + 0])); target[targetI + 1] = (double) (Math.acos(x[xI + 1])); target[targetI + 2] = (double) (Math.acos(x[xI + 2])); target[targetI + 3] = (double) (Math.acos(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.acos(x[xI + 0])); } return target; }
	public static double[] acos(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.acos(x[xI + 0])); target[targetI + 1] = (double) (Math.acos(x[xI + 1])); target[targetI + 2] = (double) (Math.acos(x[xI + 2])); target[targetI + 3] = (double) (Math.acos(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.acos(x[xI + 0])); } return target; }
	public static double[] acos(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.acos(x[xI + 0])); target[targetI + 1] = (double) (Math.acos(x[xI + 1])); target[targetI + 2] = (double) (Math.acos(x[xI + 2])); target[targetI + 3] = (double) (Math.acos(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.acos(x[xI + 0])); } return target; }
	public static double[] acos(int[] x, int xOff, double[] target, int targetOff) { return acos(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] acos(long[] x, int xOff, double[] target, int targetOff) { return acos(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] acos(short[] x, int xOff, double[] target, int targetOff) { return acos(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] acos(float[] x, int xOff, double[] target, int targetOff) { return acos(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] acos(double[] x, int xOff, double[] target, int targetOff) { return acos(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] acos(char[] x, int xOff, double[] target, int targetOff) { return acos(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] acos(int[] x, double[] target) { return acos(x, 0, target, 0); }
	public static double[] acos(long[] x, double[] target) { return acos(x, 0, target, 0); }
	public static double[] acos(short[] x, double[] target) { return acos(x, 0, target, 0); }
	public static double[] acos(float[] x, double[] target) { return acos(x, 0, target, 0); }
	public static double[] acos(double[] x, double[] target) { return acos(x, 0, target, 0); }
	public static double[] acos(char[] x, double[] target) { return acos(x, 0, target, 0); }
	public static double[] acos(int[] x) { return acos(x, new double[(x.length) / 1]); }
	public static double[] acos(long[] x) { return acos(x, new double[(x.length) / 1]); }
	public static double[] acos(short[] x) { return acos(x, new double[(x.length) / 1]); }
	public static double[] acos(float[] x) { return acos(x, new double[(x.length) / 1]); }
	public static double[] acos(double[] x) { return acos(x, new double[(x.length) / 1]); }
	public static double[] acos(char[] x) { return acos(x, new double[(x.length) / 1]); }

	// arctan
	public static double atan(double y_over_x) { return Math.atan(y_over_x); }
	public static double[] atan(int[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(y_over_xOff, y_over_x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int y_over_xI = y_over_xOff; int targetI = targetOff; { for(; i < fit; i += 4, y_over_xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); target[targetI + 1] = (double) (Math.atan(y_over_x[y_over_xI + 1])); target[targetI + 2] = (double) (Math.atan(y_over_x[y_over_xI + 2])); target[targetI + 3] = (double) (Math.atan(y_over_x[y_over_xI + 3])); } for(; i < len; i++, y_over_xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); } return target; }
	public static double[] atan(long[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(y_over_xOff, y_over_x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int y_over_xI = y_over_xOff; int targetI = targetOff; { for(; i < fit; i += 4, y_over_xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); target[targetI + 1] = (double) (Math.atan(y_over_x[y_over_xI + 1])); target[targetI + 2] = (double) (Math.atan(y_over_x[y_over_xI + 2])); target[targetI + 3] = (double) (Math.atan(y_over_x[y_over_xI + 3])); } for(; i < len; i++, y_over_xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); } return target; }
	public static double[] atan(short[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(y_over_xOff, y_over_x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int y_over_xI = y_over_xOff; int targetI = targetOff; { for(; i < fit; i += 4, y_over_xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); target[targetI + 1] = (double) (Math.atan(y_over_x[y_over_xI + 1])); target[targetI + 2] = (double) (Math.atan(y_over_x[y_over_xI + 2])); target[targetI + 3] = (double) (Math.atan(y_over_x[y_over_xI + 3])); } for(; i < len; i++, y_over_xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); } return target; }
	public static double[] atan(float[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(y_over_xOff, y_over_x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int y_over_xI = y_over_xOff; int targetI = targetOff; { for(; i < fit; i += 4, y_over_xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); target[targetI + 1] = (double) (Math.atan(y_over_x[y_over_xI + 1])); target[targetI + 2] = (double) (Math.atan(y_over_x[y_over_xI + 2])); target[targetI + 3] = (double) (Math.atan(y_over_x[y_over_xI + 3])); } for(; i < len; i++, y_over_xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); } return target; }
	public static double[] atan(double[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(y_over_xOff, y_over_x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int y_over_xI = y_over_xOff; int targetI = targetOff; { for(; i < fit; i += 4, y_over_xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); target[targetI + 1] = (double) (Math.atan(y_over_x[y_over_xI + 1])); target[targetI + 2] = (double) (Math.atan(y_over_x[y_over_xI + 2])); target[targetI + 3] = (double) (Math.atan(y_over_x[y_over_xI + 3])); } for(; i < len; i++, y_over_xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); } return target; }
	public static double[] atan(char[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(y_over_xOff, y_over_x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int y_over_xI = y_over_xOff; int targetI = targetOff; { for(; i < fit; i += 4, y_over_xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); target[targetI + 1] = (double) (Math.atan(y_over_x[y_over_xI + 1])); target[targetI + 2] = (double) (Math.atan(y_over_x[y_over_xI + 2])); target[targetI + 3] = (double) (Math.atan(y_over_x[y_over_xI + 3])); } for(; i < len; i++, y_over_xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y_over_x[y_over_xI + 0])); } return target; }
	public static double[] atan(int[] y_over_x, int y_over_xOff, double[] target, int targetOff) { return atan(y_over_x, y_over_xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan(long[] y_over_x, int y_over_xOff, double[] target, int targetOff) { return atan(y_over_x, y_over_xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan(short[] y_over_x, int y_over_xOff, double[] target, int targetOff) { return atan(y_over_x, y_over_xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan(float[] y_over_x, int y_over_xOff, double[] target, int targetOff) { return atan(y_over_x, y_over_xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan(double[] y_over_x, int y_over_xOff, double[] target, int targetOff) { return atan(y_over_x, y_over_xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan(char[] y_over_x, int y_over_xOff, double[] target, int targetOff) { return atan(y_over_x, y_over_xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan(int[] y_over_x, double[] target) { return atan(y_over_x, 0, target, 0); }
	public static double[] atan(long[] y_over_x, double[] target) { return atan(y_over_x, 0, target, 0); }
	public static double[] atan(short[] y_over_x, double[] target) { return atan(y_over_x, 0, target, 0); }
	public static double[] atan(float[] y_over_x, double[] target) { return atan(y_over_x, 0, target, 0); }
	public static double[] atan(double[] y_over_x, double[] target) { return atan(y_over_x, 0, target, 0); }
	public static double[] atan(char[] y_over_x, double[] target) { return atan(y_over_x, 0, target, 0); }
	public static double[] atan(int[] y_over_x) { return atan(y_over_x, new double[(y_over_x.length) / 1]); }
	public static double[] atan(long[] y_over_x) { return atan(y_over_x, new double[(y_over_x.length) / 1]); }
	public static double[] atan(short[] y_over_x) { return atan(y_over_x, new double[(y_over_x.length) / 1]); }
	public static double[] atan(float[] y_over_x) { return atan(y_over_x, new double[(y_over_x.length) / 1]); }
	public static double[] atan(double[] y_over_x) { return atan(y_over_x, new double[(y_over_x.length) / 1]); }
	public static double[] atan(char[] y_over_x) { return atan(y_over_x, new double[(y_over_x.length) / 1]); }

	public static double atanYX(double y, double x) { return atan(y / x); }
	public static double[] atanYX(int[] y, int yOff, int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); target[targetI + 1] = (double) (Math.atan(y[yI + 1] / x[xI + 1])); target[targetI + 2] = (double) (Math.atan(y[yI + 2] / x[xI + 2])); target[targetI + 3] = (double) (Math.atan(y[yI + 3] / x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); } return target; }
	public static double[] atanYX(long[] y, int yOff, long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); target[targetI + 1] = (double) (Math.atan(y[yI + 1] / x[xI + 1])); target[targetI + 2] = (double) (Math.atan(y[yI + 2] / x[xI + 2])); target[targetI + 3] = (double) (Math.atan(y[yI + 3] / x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); } return target; }
	public static double[] atanYX(short[] y, int yOff, short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); target[targetI + 1] = (double) (Math.atan(y[yI + 1] / x[xI + 1])); target[targetI + 2] = (double) (Math.atan(y[yI + 2] / x[xI + 2])); target[targetI + 3] = (double) (Math.atan(y[yI + 3] / x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); } return target; }
	public static double[] atanYX(float[] y, int yOff, float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); target[targetI + 1] = (double) (Math.atan(y[yI + 1] / x[xI + 1])); target[targetI + 2] = (double) (Math.atan(y[yI + 2] / x[xI + 2])); target[targetI + 3] = (double) (Math.atan(y[yI + 3] / x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); } return target; }
	public static double[] atanYX(double[] y, int yOff, double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); target[targetI + 1] = (double) (Math.atan(y[yI + 1] / x[xI + 1])); target[targetI + 2] = (double) (Math.atan(y[yI + 2] / x[xI + 2])); target[targetI + 3] = (double) (Math.atan(y[yI + 3] / x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); } return target; }
	public static double[] atanYX(char[] y, int yOff, char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); target[targetI + 1] = (double) (Math.atan(y[yI + 1] / x[xI + 1])); target[targetI + 2] = (double) (Math.atan(y[yI + 2] / x[xI + 2])); target[targetI + 3] = (double) (Math.atan(y[yI + 3] / x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan(y[yI + 0] / x[xI + 0])); } return target; }
	public static double[] atanYX(int[] y, int yOff, int[] x, int xOff, double[] target, int targetOff) { return atanYX(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atanYX(long[] y, int yOff, long[] x, int xOff, double[] target, int targetOff) { return atanYX(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atanYX(short[] y, int yOff, short[] x, int xOff, double[] target, int targetOff) { return atanYX(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atanYX(float[] y, int yOff, float[] x, int xOff, double[] target, int targetOff) { return atanYX(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atanYX(double[] y, int yOff, double[] x, int xOff, double[] target, int targetOff) { return atanYX(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atanYX(char[] y, int yOff, char[] x, int xOff, double[] target, int targetOff) { return atanYX(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atanYX(int[] y, int[] x, double[] target) { return atanYX(y, 0, x, 0, target, 0); }
	public static double[] atanYX(long[] y, long[] x, double[] target) { return atanYX(y, 0, x, 0, target, 0); }
	public static double[] atanYX(short[] y, short[] x, double[] target) { return atanYX(y, 0, x, 0, target, 0); }
	public static double[] atanYX(float[] y, float[] x, double[] target) { return atanYX(y, 0, x, 0, target, 0); }
	public static double[] atanYX(double[] y, double[] x, double[] target) { return atanYX(y, 0, x, 0, target, 0); }
	public static double[] atanYX(char[] y, char[] x, double[] target) { return atanYX(y, 0, x, 0, target, 0); }
	public static double[] atanYX(int[] y, int[] x) { return atanYX(y, x, new double[(y.length + x.length) / 2]); }
	public static double[] atanYX(long[] y, long[] x) { return atanYX(y, x, new double[(y.length + x.length) / 2]); }
	public static double[] atanYX(short[] y, short[] x) { return atanYX(y, x, new double[(y.length + x.length) / 2]); }
	public static double[] atanYX(float[] y, float[] x) { return atanYX(y, x, new double[(y.length + x.length) / 2]); }
	public static double[] atanYX(double[] y, double[] x) { return atanYX(y, x, new double[(y.length + x.length) / 2]); }
	public static double[] atanYX(char[] y, char[] x) { return atanYX(y, x, new double[(y.length + x.length) / 2]); }

	public static double atan2(double y, double x) { return Math.atan2(y, x); }
	public static double[] atan2(int[] y, int yOff, int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); target[targetI + 1] = (double) (Math.atan2(y[yI + 1], x[xI + 1])); target[targetI + 2] = (double) (Math.atan2(y[yI + 2], x[xI + 2])); target[targetI + 3] = (double) (Math.atan2(y[yI + 3], x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); } return target; }
	public static double[] atan2(long[] y, int yOff, long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); target[targetI + 1] = (double) (Math.atan2(y[yI + 1], x[xI + 1])); target[targetI + 2] = (double) (Math.atan2(y[yI + 2], x[xI + 2])); target[targetI + 3] = (double) (Math.atan2(y[yI + 3], x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); } return target; }
	public static double[] atan2(short[] y, int yOff, short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); target[targetI + 1] = (double) (Math.atan2(y[yI + 1], x[xI + 1])); target[targetI + 2] = (double) (Math.atan2(y[yI + 2], x[xI + 2])); target[targetI + 3] = (double) (Math.atan2(y[yI + 3], x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); } return target; }
	public static double[] atan2(float[] y, int yOff, float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); target[targetI + 1] = (double) (Math.atan2(y[yI + 1], x[xI + 1])); target[targetI + 2] = (double) (Math.atan2(y[yI + 2], x[xI + 2])); target[targetI + 3] = (double) (Math.atan2(y[yI + 3], x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); } return target; }
	public static double[] atan2(double[] y, int yOff, double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); target[targetI + 1] = (double) (Math.atan2(y[yI + 1], x[xI + 1])); target[targetI + 2] = (double) (Math.atan2(y[yI + 2], x[xI + 2])); target[targetI + 3] = (double) (Math.atan2(y[yI + 3], x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); } return target; }
	public static double[] atan2(char[] y, int yOff, char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int yI = yOff; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, yI += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); target[targetI + 1] = (double) (Math.atan2(y[yI + 1], x[xI + 1])); target[targetI + 2] = (double) (Math.atan2(y[yI + 2], x[xI + 2])); target[targetI + 3] = (double) (Math.atan2(y[yI + 3], x[xI + 3])); } for(; i < len; i++, yI++, xI++, targetI++) target[targetI + 0] = (double) (Math.atan2(y[yI + 0], x[xI + 0])); } return target; }
	public static double[] atan2(int[] y, int yOff, int[] x, int xOff, double[] target, int targetOff) { return atan2(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan2(long[] y, int yOff, long[] x, int xOff, double[] target, int targetOff) { return atan2(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan2(short[] y, int yOff, short[] x, int xOff, double[] target, int targetOff) { return atan2(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan2(float[] y, int yOff, float[] x, int xOff, double[] target, int targetOff) { return atan2(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan2(double[] y, int yOff, double[] x, int xOff, double[] target, int targetOff) { return atan2(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan2(char[] y, int yOff, char[] x, int xOff, double[] target, int targetOff) { return atan2(y, yOff, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] atan2(int[] y, int[] x, double[] target) { return atan2(y, 0, x, 0, target, 0); }
	public static double[] atan2(long[] y, long[] x, double[] target) { return atan2(y, 0, x, 0, target, 0); }
	public static double[] atan2(short[] y, short[] x, double[] target) { return atan2(y, 0, x, 0, target, 0); }
	public static double[] atan2(float[] y, float[] x, double[] target) { return atan2(y, 0, x, 0, target, 0); }
	public static double[] atan2(double[] y, double[] x, double[] target) { return atan2(y, 0, x, 0, target, 0); }
	public static double[] atan2(char[] y, char[] x, double[] target) { return atan2(y, 0, x, 0, target, 0); }
	public static double[] atan2(int[] y, int[] x) { return atan2(y, x, new double[(y.length + x.length) / 2]); }
	public static double[] atan2(long[] y, long[] x) { return atan2(y, x, new double[(y.length + x.length) / 2]); }
	public static double[] atan2(short[] y, short[] x) { return atan2(y, x, new double[(y.length + x.length) / 2]); }
	public static double[] atan2(float[] y, float[] x) { return atan2(y, x, new double[(y.length + x.length) / 2]); }
	public static double[] atan2(double[] y, double[] x) { return atan2(y, x, new double[(y.length + x.length) / 2]); }
	public static double[] atan2(char[] y, char[] x) { return atan2(y, x, new double[(y.length + x.length) / 2]); }

	// pow
	public static double pow(double x, double y) { return Math.pow(x, y); }
	public static int[] pow(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (int) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (int) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (int) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (int) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (int) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static long[] pow(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (long) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (long) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (long) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (long) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (long) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static short[] pow(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (short) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (short) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (short) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (short) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (short) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static float[] pow(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (float) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (float) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (float) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (float) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (float) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] pow(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static char[] pow(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (char) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (char) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (char) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (char) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (char) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static int[] pow(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff) { return pow(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static long[] pow(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff) { return pow(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static short[] pow(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff) { return pow(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static float[] pow(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff) { return pow(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] pow(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff) { return pow(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static char[] pow(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff) { return pow(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static int[] pow(int[] x, int[] y, int[] target) { return pow(x, 0, y, 0, target, 0); }
	public static long[] pow(long[] x, long[] y, long[] target) { return pow(x, 0, y, 0, target, 0); }
	public static short[] pow(short[] x, short[] y, short[] target) { return pow(x, 0, y, 0, target, 0); }
	public static float[] pow(float[] x, float[] y, float[] target) { return pow(x, 0, y, 0, target, 0); }
	public static double[] pow(double[] x, double[] y, double[] target) { return pow(x, 0, y, 0, target, 0); }
	public static char[] pow(char[] x, char[] y, char[] target) { return pow(x, 0, y, 0, target, 0); }
	public static int[] pow(int[] x, int[] y) { return pow(x, y, new int[(x.length + y.length) / 2]); }
	public static long[] pow(long[] x, long[] y) { return pow(x, y, new long[(x.length + y.length) / 2]); }
	public static short[] pow(short[] x, short[] y) { return pow(x, y, new short[(x.length + y.length) / 2]); }
	public static float[] pow(float[] x, float[] y) { return pow(x, y, new float[(x.length + y.length) / 2]); }
	public static double[] pow(double[] x, double[] y) { return pow(x, y, new double[(x.length + y.length) / 2]); }
	public static char[] pow(char[] x, char[] y) { return pow(x, y, new char[(x.length + y.length) / 2]); }

	protected static double powD(double x, double y) { return (double) Math.pow(x, y); }
	public static double[] powD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] powD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] powD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] powD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] powD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] powD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.pow(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.pow(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.pow(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.pow(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] powD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff) { return powD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] powD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff) { return powD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] powD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff) { return powD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] powD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff) { return powD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] powD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff) { return powD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] powD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff) { return powD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] powD(int[] x, int[] y, double[] target) { return powD(x, 0, y, 0, target, 0); }
	public static double[] powD(long[] x, long[] y, double[] target) { return powD(x, 0, y, 0, target, 0); }
	public static double[] powD(short[] x, short[] y, double[] target) { return powD(x, 0, y, 0, target, 0); }
	public static double[] powD(float[] x, float[] y, double[] target) { return powD(x, 0, y, 0, target, 0); }
	public static double[] powD(double[] x, double[] y, double[] target) { return powD(x, 0, y, 0, target, 0); }
	public static double[] powD(char[] x, char[] y, double[] target) { return powD(x, 0, y, 0, target, 0); }
	public static double[] powD(int[] x, int[] y) { return powD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] powD(long[] x, long[] y) { return powD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] powD(short[] x, short[] y) { return powD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] powD(float[] x, float[] y) { return powD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] powD(double[] x, double[] y) { return powD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] powD(char[] x, char[] y) { return powD(x, y, new double[(x.length + y.length) / 2]); }

	// exp
	public static double exp(double x) { return Math.exp(x); }
	public static double[] exp(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.exp(x[xI + 0])); target[targetI + 1] = (double) (Math.exp(x[xI + 1])); target[targetI + 2] = (double) (Math.exp(x[xI + 2])); target[targetI + 3] = (double) (Math.exp(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.exp(x[xI + 0])); } return target; }
	public static double[] exp(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.exp(x[xI + 0])); target[targetI + 1] = (double) (Math.exp(x[xI + 1])); target[targetI + 2] = (double) (Math.exp(x[xI + 2])); target[targetI + 3] = (double) (Math.exp(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.exp(x[xI + 0])); } return target; }
	public static double[] exp(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.exp(x[xI + 0])); target[targetI + 1] = (double) (Math.exp(x[xI + 1])); target[targetI + 2] = (double) (Math.exp(x[xI + 2])); target[targetI + 3] = (double) (Math.exp(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.exp(x[xI + 0])); } return target; }
	public static double[] exp(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.exp(x[xI + 0])); target[targetI + 1] = (double) (Math.exp(x[xI + 1])); target[targetI + 2] = (double) (Math.exp(x[xI + 2])); target[targetI + 3] = (double) (Math.exp(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.exp(x[xI + 0])); } return target; }
	public static double[] exp(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.exp(x[xI + 0])); target[targetI + 1] = (double) (Math.exp(x[xI + 1])); target[targetI + 2] = (double) (Math.exp(x[xI + 2])); target[targetI + 3] = (double) (Math.exp(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.exp(x[xI + 0])); } return target; }
	public static double[] exp(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.exp(x[xI + 0])); target[targetI + 1] = (double) (Math.exp(x[xI + 1])); target[targetI + 2] = (double) (Math.exp(x[xI + 2])); target[targetI + 3] = (double) (Math.exp(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.exp(x[xI + 0])); } return target; }
	public static double[] exp(int[] x, int xOff, double[] target, int targetOff) { return exp(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp(long[] x, int xOff, double[] target, int targetOff) { return exp(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp(short[] x, int xOff, double[] target, int targetOff) { return exp(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp(float[] x, int xOff, double[] target, int targetOff) { return exp(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp(double[] x, int xOff, double[] target, int targetOff) { return exp(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp(char[] x, int xOff, double[] target, int targetOff) { return exp(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp(int[] x, double[] target) { return exp(x, 0, target, 0); }
	public static double[] exp(long[] x, double[] target) { return exp(x, 0, target, 0); }
	public static double[] exp(short[] x, double[] target) { return exp(x, 0, target, 0); }
	public static double[] exp(float[] x, double[] target) { return exp(x, 0, target, 0); }
	public static double[] exp(double[] x, double[] target) { return exp(x, 0, target, 0); }
	public static double[] exp(char[] x, double[] target) { return exp(x, 0, target, 0); }
	public static double[] exp(int[] x) { return exp(x, new double[(x.length) / 1]); }
	public static double[] exp(long[] x) { return exp(x, new double[(x.length) / 1]); }
	public static double[] exp(short[] x) { return exp(x, new double[(x.length) / 1]); }
	public static double[] exp(float[] x) { return exp(x, new double[(x.length) / 1]); }
	public static double[] exp(double[] x) { return exp(x, new double[(x.length) / 1]); }
	public static double[] exp(char[] x) { return exp(x, new double[(x.length) / 1]); }

	// log
	public static double log(double x) { return Math.log(x); }
	public static double[] log(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0])); target[targetI + 1] = (double) (Math.log(x[xI + 1])); target[targetI + 2] = (double) (Math.log(x[xI + 2])); target[targetI + 3] = (double) (Math.log(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0])); } return target; }
	public static double[] log(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0])); target[targetI + 1] = (double) (Math.log(x[xI + 1])); target[targetI + 2] = (double) (Math.log(x[xI + 2])); target[targetI + 3] = (double) (Math.log(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0])); } return target; }
	public static double[] log(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0])); target[targetI + 1] = (double) (Math.log(x[xI + 1])); target[targetI + 2] = (double) (Math.log(x[xI + 2])); target[targetI + 3] = (double) (Math.log(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0])); } return target; }
	public static double[] log(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0])); target[targetI + 1] = (double) (Math.log(x[xI + 1])); target[targetI + 2] = (double) (Math.log(x[xI + 2])); target[targetI + 3] = (double) (Math.log(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0])); } return target; }
	public static double[] log(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0])); target[targetI + 1] = (double) (Math.log(x[xI + 1])); target[targetI + 2] = (double) (Math.log(x[xI + 2])); target[targetI + 3] = (double) (Math.log(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0])); } return target; }
	public static double[] log(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0])); target[targetI + 1] = (double) (Math.log(x[xI + 1])); target[targetI + 2] = (double) (Math.log(x[xI + 2])); target[targetI + 3] = (double) (Math.log(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0])); } return target; }
	public static double[] log(int[] x, int xOff, double[] target, int targetOff) { return log(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log(long[] x, int xOff, double[] target, int targetOff) { return log(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log(short[] x, int xOff, double[] target, int targetOff) { return log(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log(float[] x, int xOff, double[] target, int targetOff) { return log(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log(double[] x, int xOff, double[] target, int targetOff) { return log(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log(char[] x, int xOff, double[] target, int targetOff) { return log(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log(int[] x, double[] target) { return log(x, 0, target, 0); }
	public static double[] log(long[] x, double[] target) { return log(x, 0, target, 0); }
	public static double[] log(short[] x, double[] target) { return log(x, 0, target, 0); }
	public static double[] log(float[] x, double[] target) { return log(x, 0, target, 0); }
	public static double[] log(double[] x, double[] target) { return log(x, 0, target, 0); }
	public static double[] log(char[] x, double[] target) { return log(x, 0, target, 0); }
	public static double[] log(int[] x) { return log(x, new double[(x.length) / 1]); }
	public static double[] log(long[] x) { return log(x, new double[(x.length) / 1]); }
	public static double[] log(short[] x) { return log(x, new double[(x.length) / 1]); }
	public static double[] log(float[] x) { return log(x, new double[(x.length) / 1]); }
	public static double[] log(double[] x) { return log(x, new double[(x.length) / 1]); }
	public static double[] log(char[] x) { return log(x, new double[(x.length) / 1]); }

	// exp2
	public static double exp2(double x) { return Math.pow(2, x); }
	public static double[] exp2(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); target[targetI + 1] = (double) (Math.pow(2, x[xI + 1])); target[targetI + 2] = (double) (Math.pow(2, x[xI + 2])); target[targetI + 3] = (double) (Math.pow(2, x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); } return target; }
	public static double[] exp2(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); target[targetI + 1] = (double) (Math.pow(2, x[xI + 1])); target[targetI + 2] = (double) (Math.pow(2, x[xI + 2])); target[targetI + 3] = (double) (Math.pow(2, x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); } return target; }
	public static double[] exp2(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); target[targetI + 1] = (double) (Math.pow(2, x[xI + 1])); target[targetI + 2] = (double) (Math.pow(2, x[xI + 2])); target[targetI + 3] = (double) (Math.pow(2, x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); } return target; }
	public static double[] exp2(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); target[targetI + 1] = (double) (Math.pow(2, x[xI + 1])); target[targetI + 2] = (double) (Math.pow(2, x[xI + 2])); target[targetI + 3] = (double) (Math.pow(2, x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); } return target; }
	public static double[] exp2(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); target[targetI + 1] = (double) (Math.pow(2, x[xI + 1])); target[targetI + 2] = (double) (Math.pow(2, x[xI + 2])); target[targetI + 3] = (double) (Math.pow(2, x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); } return target; }
	public static double[] exp2(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); target[targetI + 1] = (double) (Math.pow(2, x[xI + 1])); target[targetI + 2] = (double) (Math.pow(2, x[xI + 2])); target[targetI + 3] = (double) (Math.pow(2, x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.pow(2, x[xI + 0])); } return target; }
	public static double[] exp2(int[] x, int xOff, double[] target, int targetOff) { return exp2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp2(long[] x, int xOff, double[] target, int targetOff) { return exp2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp2(short[] x, int xOff, double[] target, int targetOff) { return exp2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp2(float[] x, int xOff, double[] target, int targetOff) { return exp2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp2(double[] x, int xOff, double[] target, int targetOff) { return exp2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp2(char[] x, int xOff, double[] target, int targetOff) { return exp2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] exp2(int[] x, double[] target) { return exp2(x, 0, target, 0); }
	public static double[] exp2(long[] x, double[] target) { return exp2(x, 0, target, 0); }
	public static double[] exp2(short[] x, double[] target) { return exp2(x, 0, target, 0); }
	public static double[] exp2(float[] x, double[] target) { return exp2(x, 0, target, 0); }
	public static double[] exp2(double[] x, double[] target) { return exp2(x, 0, target, 0); }
	public static double[] exp2(char[] x, double[] target) { return exp2(x, 0, target, 0); }
	public static double[] exp2(int[] x) { return exp2(x, new double[(x.length) / 1]); }
	public static double[] exp2(long[] x) { return exp2(x, new double[(x.length) / 1]); }
	public static double[] exp2(short[] x) { return exp2(x, new double[(x.length) / 1]); }
	public static double[] exp2(float[] x) { return exp2(x, new double[(x.length) / 1]); }
	public static double[] exp2(double[] x) { return exp2(x, new double[(x.length) / 1]); }
	public static double[] exp2(char[] x) { return exp2(x, new double[(x.length) / 1]); }

	// log2
	public static double log2(double x) { return Math.log(x) / Math.log(2); }
	public static double[] log2(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); target[targetI + 1] = (double) (Math.log(x[xI + 1]) / Math.log(2)); target[targetI + 2] = (double) (Math.log(x[xI + 2]) / Math.log(2)); target[targetI + 3] = (double) (Math.log(x[xI + 3]) / Math.log(2)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); } return target; }
	public static double[] log2(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); target[targetI + 1] = (double) (Math.log(x[xI + 1]) / Math.log(2)); target[targetI + 2] = (double) (Math.log(x[xI + 2]) / Math.log(2)); target[targetI + 3] = (double) (Math.log(x[xI + 3]) / Math.log(2)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); } return target; }
	public static double[] log2(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); target[targetI + 1] = (double) (Math.log(x[xI + 1]) / Math.log(2)); target[targetI + 2] = (double) (Math.log(x[xI + 2]) / Math.log(2)); target[targetI + 3] = (double) (Math.log(x[xI + 3]) / Math.log(2)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); } return target; }
	public static double[] log2(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); target[targetI + 1] = (double) (Math.log(x[xI + 1]) / Math.log(2)); target[targetI + 2] = (double) (Math.log(x[xI + 2]) / Math.log(2)); target[targetI + 3] = (double) (Math.log(x[xI + 3]) / Math.log(2)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); } return target; }
	public static double[] log2(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); target[targetI + 1] = (double) (Math.log(x[xI + 1]) / Math.log(2)); target[targetI + 2] = (double) (Math.log(x[xI + 2]) / Math.log(2)); target[targetI + 3] = (double) (Math.log(x[xI + 3]) / Math.log(2)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); } return target; }
	public static double[] log2(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); target[targetI + 1] = (double) (Math.log(x[xI + 1]) / Math.log(2)); target[targetI + 2] = (double) (Math.log(x[xI + 2]) / Math.log(2)); target[targetI + 3] = (double) (Math.log(x[xI + 3]) / Math.log(2)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.log(x[xI + 0]) / Math.log(2)); } return target; }
	public static double[] log2(int[] x, int xOff, double[] target, int targetOff) { return log2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log2(long[] x, int xOff, double[] target, int targetOff) { return log2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log2(short[] x, int xOff, double[] target, int targetOff) { return log2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log2(float[] x, int xOff, double[] target, int targetOff) { return log2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log2(double[] x, int xOff, double[] target, int targetOff) { return log2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log2(char[] x, int xOff, double[] target, int targetOff) { return log2(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] log2(int[] x, double[] target) { return log2(x, 0, target, 0); }
	public static double[] log2(long[] x, double[] target) { return log2(x, 0, target, 0); }
	public static double[] log2(short[] x, double[] target) { return log2(x, 0, target, 0); }
	public static double[] log2(float[] x, double[] target) { return log2(x, 0, target, 0); }
	public static double[] log2(double[] x, double[] target) { return log2(x, 0, target, 0); }
	public static double[] log2(char[] x, double[] target) { return log2(x, 0, target, 0); }
	public static double[] log2(int[] x) { return log2(x, new double[(x.length) / 1]); }
	public static double[] log2(long[] x) { return log2(x, new double[(x.length) / 1]); }
	public static double[] log2(short[] x) { return log2(x, new double[(x.length) / 1]); }
	public static double[] log2(float[] x) { return log2(x, new double[(x.length) / 1]); }
	public static double[] log2(double[] x) { return log2(x, new double[(x.length) / 1]); }
	public static double[] log2(char[] x) { return log2(x, new double[(x.length) / 1]); }

	// sqrt
	public static double sqrt(double x) { return Math.sqrt(x); }
	public static double[] sqrt(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); target[targetI + 1] = (double) (Math.sqrt(x[xI + 1])); target[targetI + 2] = (double) (Math.sqrt(x[xI + 2])); target[targetI + 3] = (double) (Math.sqrt(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); } return target; }
	public static double[] sqrt(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); target[targetI + 1] = (double) (Math.sqrt(x[xI + 1])); target[targetI + 2] = (double) (Math.sqrt(x[xI + 2])); target[targetI + 3] = (double) (Math.sqrt(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); } return target; }
	public static double[] sqrt(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); target[targetI + 1] = (double) (Math.sqrt(x[xI + 1])); target[targetI + 2] = (double) (Math.sqrt(x[xI + 2])); target[targetI + 3] = (double) (Math.sqrt(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); } return target; }
	public static double[] sqrt(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); target[targetI + 1] = (double) (Math.sqrt(x[xI + 1])); target[targetI + 2] = (double) (Math.sqrt(x[xI + 2])); target[targetI + 3] = (double) (Math.sqrt(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); } return target; }
	public static double[] sqrt(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); target[targetI + 1] = (double) (Math.sqrt(x[xI + 1])); target[targetI + 2] = (double) (Math.sqrt(x[xI + 2])); target[targetI + 3] = (double) (Math.sqrt(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); } return target; }
	public static double[] sqrt(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); target[targetI + 1] = (double) (Math.sqrt(x[xI + 1])); target[targetI + 2] = (double) (Math.sqrt(x[xI + 2])); target[targetI + 3] = (double) (Math.sqrt(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.sqrt(x[xI + 0])); } return target; }
	public static double[] sqrt(int[] x, int xOff, double[] target, int targetOff) { return sqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] sqrt(long[] x, int xOff, double[] target, int targetOff) { return sqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] sqrt(short[] x, int xOff, double[] target, int targetOff) { return sqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] sqrt(float[] x, int xOff, double[] target, int targetOff) { return sqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] sqrt(double[] x, int xOff, double[] target, int targetOff) { return sqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] sqrt(char[] x, int xOff, double[] target, int targetOff) { return sqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] sqrt(int[] x, double[] target) { return sqrt(x, 0, target, 0); }
	public static double[] sqrt(long[] x, double[] target) { return sqrt(x, 0, target, 0); }
	public static double[] sqrt(short[] x, double[] target) { return sqrt(x, 0, target, 0); }
	public static double[] sqrt(float[] x, double[] target) { return sqrt(x, 0, target, 0); }
	public static double[] sqrt(double[] x, double[] target) { return sqrt(x, 0, target, 0); }
	public static double[] sqrt(char[] x, double[] target) { return sqrt(x, 0, target, 0); }
	public static double[] sqrt(int[] x) { return sqrt(x, new double[(x.length) / 1]); }
	public static double[] sqrt(long[] x) { return sqrt(x, new double[(x.length) / 1]); }
	public static double[] sqrt(short[] x) { return sqrt(x, new double[(x.length) / 1]); }
	public static double[] sqrt(float[] x) { return sqrt(x, new double[(x.length) / 1]); }
	public static double[] sqrt(double[] x) { return sqrt(x, new double[(x.length) / 1]); }
	public static double[] sqrt(char[] x) { return sqrt(x, new double[(x.length) / 1]); }

	// inverse sqrt
	// https://stackoverflow.com/questions/11513344/how-to-implement-the-fast-inverse-square-root-in-java
	public static double inversesqrt(double x) {
		double xhalf = 0.5d * x;
		long i = Double.doubleToLongBits(x);
		i = 0x5fe6ec85e7de30daL - (i >> 1);
		x = Double.longBitsToDouble(i);
		x *= (1.5d - xhalf * x * x);
		return x;
	}
	public static double[] inversesqrt(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) inversesqrt(x[xI + 0]); target[targetI + 1] = (double) inversesqrt(x[xI + 1]); target[targetI + 2] = (double) inversesqrt(x[xI + 2]); target[targetI + 3] = (double) inversesqrt(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) inversesqrt(x[xI + 0]); } return target; }
	public static double[] inversesqrt(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) inversesqrt(x[xI + 0]); target[targetI + 1] = (double) inversesqrt(x[xI + 1]); target[targetI + 2] = (double) inversesqrt(x[xI + 2]); target[targetI + 3] = (double) inversesqrt(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) inversesqrt(x[xI + 0]); } return target; }
	public static double[] inversesqrt(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) inversesqrt(x[xI + 0]); target[targetI + 1] = (double) inversesqrt(x[xI + 1]); target[targetI + 2] = (double) inversesqrt(x[xI + 2]); target[targetI + 3] = (double) inversesqrt(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) inversesqrt(x[xI + 0]); } return target; }
	public static double[] inversesqrt(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) inversesqrt(x[xI + 0]); target[targetI + 1] = (double) inversesqrt(x[xI + 1]); target[targetI + 2] = (double) inversesqrt(x[xI + 2]); target[targetI + 3] = (double) inversesqrt(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) inversesqrt(x[xI + 0]); } return target; }
	public static double[] inversesqrt(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) inversesqrt(x[xI + 0]); target[targetI + 1] = (double) inversesqrt(x[xI + 1]); target[targetI + 2] = (double) inversesqrt(x[xI + 2]); target[targetI + 3] = (double) inversesqrt(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) inversesqrt(x[xI + 0]); } return target; }
	public static double[] inversesqrt(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) inversesqrt(x[xI + 0]); target[targetI + 1] = (double) inversesqrt(x[xI + 1]); target[targetI + 2] = (double) inversesqrt(x[xI + 2]); target[targetI + 3] = (double) inversesqrt(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) inversesqrt(x[xI + 0]); } return target; }
	public static double[] inversesqrt(int[] x, int xOff, double[] target, int targetOff) { return inversesqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] inversesqrt(long[] x, int xOff, double[] target, int targetOff) { return inversesqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] inversesqrt(short[] x, int xOff, double[] target, int targetOff) { return inversesqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] inversesqrt(float[] x, int xOff, double[] target, int targetOff) { return inversesqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] inversesqrt(double[] x, int xOff, double[] target, int targetOff) { return inversesqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] inversesqrt(char[] x, int xOff, double[] target, int targetOff) { return inversesqrt(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] inversesqrt(int[] x, double[] target) { return inversesqrt(x, 0, target, 0); }
	public static double[] inversesqrt(long[] x, double[] target) { return inversesqrt(x, 0, target, 0); }
	public static double[] inversesqrt(short[] x, double[] target) { return inversesqrt(x, 0, target, 0); }
	public static double[] inversesqrt(float[] x, double[] target) { return inversesqrt(x, 0, target, 0); }
	public static double[] inversesqrt(double[] x, double[] target) { return inversesqrt(x, 0, target, 0); }
	public static double[] inversesqrt(char[] x, double[] target) { return inversesqrt(x, 0, target, 0); }
	public static double[] inversesqrt(int[] x) { return inversesqrt(x, new double[(x.length) / 1]); }
	public static double[] inversesqrt(long[] x) { return inversesqrt(x, new double[(x.length) / 1]); }
	public static double[] inversesqrt(short[] x) { return inversesqrt(x, new double[(x.length) / 1]); }
	public static double[] inversesqrt(float[] x) { return inversesqrt(x, new double[(x.length) / 1]); }
	public static double[] inversesqrt(double[] x) { return inversesqrt(x, new double[(x.length) / 1]); }
	public static double[] inversesqrt(char[] x) { return inversesqrt(x, new double[(x.length) / 1]); }

	// abs
	public static double abs(double x) { return Math.abs(x); }
	public static int[] abs(int[] x, int xOff, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (int) (Math.abs(x[xI + 0])); target[targetI + 1] = (int) (Math.abs(x[xI + 1])); target[targetI + 2] = (int) (Math.abs(x[xI + 2])); target[targetI + 3] = (int) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (int) (Math.abs(x[xI + 0])); } return target; }
	public static long[] abs(long[] x, int xOff, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (long) (Math.abs(x[xI + 0])); target[targetI + 1] = (long) (Math.abs(x[xI + 1])); target[targetI + 2] = (long) (Math.abs(x[xI + 2])); target[targetI + 3] = (long) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (long) (Math.abs(x[xI + 0])); } return target; }
	public static short[] abs(short[] x, int xOff, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (short) (Math.abs(x[xI + 0])); target[targetI + 1] = (short) (Math.abs(x[xI + 1])); target[targetI + 2] = (short) (Math.abs(x[xI + 2])); target[targetI + 3] = (short) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (short) (Math.abs(x[xI + 0])); } return target; }
	public static float[] abs(float[] x, int xOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (float) (Math.abs(x[xI + 0])); target[targetI + 1] = (float) (Math.abs(x[xI + 1])); target[targetI + 2] = (float) (Math.abs(x[xI + 2])); target[targetI + 3] = (float) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (float) (Math.abs(x[xI + 0])); } return target; }
	public static double[] abs(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.abs(x[xI + 0])); target[targetI + 1] = (double) (Math.abs(x[xI + 1])); target[targetI + 2] = (double) (Math.abs(x[xI + 2])); target[targetI + 3] = (double) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.abs(x[xI + 0])); } return target; }
	public static char[] abs(char[] x, int xOff, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (char) (Math.abs(x[xI + 0])); target[targetI + 1] = (char) (Math.abs(x[xI + 1])); target[targetI + 2] = (char) (Math.abs(x[xI + 2])); target[targetI + 3] = (char) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (char) (Math.abs(x[xI + 0])); } return target; }
	public static int[] abs(int[] x, int xOff, int[] target, int targetOff) { return abs(x, xOff, target, targetOff, target.length - targetOff); }
	public static long[] abs(long[] x, int xOff, long[] target, int targetOff) { return abs(x, xOff, target, targetOff, target.length - targetOff); }
	public static short[] abs(short[] x, int xOff, short[] target, int targetOff) { return abs(x, xOff, target, targetOff, target.length - targetOff); }
	public static float[] abs(float[] x, int xOff, float[] target, int targetOff) { return abs(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] abs(double[] x, int xOff, double[] target, int targetOff) { return abs(x, xOff, target, targetOff, target.length - targetOff); }
	public static char[] abs(char[] x, int xOff, char[] target, int targetOff) { return abs(x, xOff, target, targetOff, target.length - targetOff); }
	public static int[] abs(int[] x, int[] target) { return abs(x, 0, target, 0); }
	public static long[] abs(long[] x, long[] target) { return abs(x, 0, target, 0); }
	public static short[] abs(short[] x, short[] target) { return abs(x, 0, target, 0); }
	public static float[] abs(float[] x, float[] target) { return abs(x, 0, target, 0); }
	public static double[] abs(double[] x, double[] target) { return abs(x, 0, target, 0); }
	public static char[] abs(char[] x, char[] target) { return abs(x, 0, target, 0); }
	public static int[] abs(int[] x) { return abs(x, new int[(x.length) / 1]); }
	public static long[] abs(long[] x) { return abs(x, new long[(x.length) / 1]); }
	public static short[] abs(short[] x) { return abs(x, new short[(x.length) / 1]); }
	public static float[] abs(float[] x) { return abs(x, new float[(x.length) / 1]); }
	public static double[] abs(double[] x) { return abs(x, new double[(x.length) / 1]); }
	public static char[] abs(char[] x) { return abs(x, new char[(x.length) / 1]); }

	protected static double absD(double x) { return (double) Math.abs(x); }
	public static double[] absD(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.abs(x[xI + 0])); target[targetI + 1] = (double) (Math.abs(x[xI + 1])); target[targetI + 2] = (double) (Math.abs(x[xI + 2])); target[targetI + 3] = (double) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.abs(x[xI + 0])); } return target; }
	public static double[] absD(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.abs(x[xI + 0])); target[targetI + 1] = (double) (Math.abs(x[xI + 1])); target[targetI + 2] = (double) (Math.abs(x[xI + 2])); target[targetI + 3] = (double) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.abs(x[xI + 0])); } return target; }
	public static double[] absD(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.abs(x[xI + 0])); target[targetI + 1] = (double) (Math.abs(x[xI + 1])); target[targetI + 2] = (double) (Math.abs(x[xI + 2])); target[targetI + 3] = (double) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.abs(x[xI + 0])); } return target; }
	public static double[] absD(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.abs(x[xI + 0])); target[targetI + 1] = (double) (Math.abs(x[xI + 1])); target[targetI + 2] = (double) (Math.abs(x[xI + 2])); target[targetI + 3] = (double) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.abs(x[xI + 0])); } return target; }
	public static double[] absD(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.abs(x[xI + 0])); target[targetI + 1] = (double) (Math.abs(x[xI + 1])); target[targetI + 2] = (double) (Math.abs(x[xI + 2])); target[targetI + 3] = (double) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.abs(x[xI + 0])); } return target; }
	public static double[] absD(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.abs(x[xI + 0])); target[targetI + 1] = (double) (Math.abs(x[xI + 1])); target[targetI + 2] = (double) (Math.abs(x[xI + 2])); target[targetI + 3] = (double) (Math.abs(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.abs(x[xI + 0])); } return target; }
	public static double[] absD(int[] x, int xOff, double[] target, int targetOff) { return absD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] absD(long[] x, int xOff, double[] target, int targetOff) { return absD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] absD(short[] x, int xOff, double[] target, int targetOff) { return absD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] absD(float[] x, int xOff, double[] target, int targetOff) { return absD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] absD(double[] x, int xOff, double[] target, int targetOff) { return absD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] absD(char[] x, int xOff, double[] target, int targetOff) { return absD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] absD(int[] x, double[] target) { return absD(x, 0, target, 0); }
	public static double[] absD(long[] x, double[] target) { return absD(x, 0, target, 0); }
	public static double[] absD(short[] x, double[] target) { return absD(x, 0, target, 0); }
	public static double[] absD(float[] x, double[] target) { return absD(x, 0, target, 0); }
	public static double[] absD(double[] x, double[] target) { return absD(x, 0, target, 0); }
	public static double[] absD(char[] x, double[] target) { return absD(x, 0, target, 0); }
	public static double[] absD(int[] x) { return absD(x, new double[(x.length) / 1]); }
	public static double[] absD(long[] x) { return absD(x, new double[(x.length) / 1]); }
	public static double[] absD(short[] x) { return absD(x, new double[(x.length) / 1]); }
	public static double[] absD(float[] x) { return absD(x, new double[(x.length) / 1]); }
	public static double[] absD(double[] x) { return absD(x, new double[(x.length) / 1]); }
	public static double[] absD(char[] x) { return absD(x, new double[(x.length) / 1]); }

	// sign
	public static int sign(int x) { return (int) (x < 0 ? -1 : x == 0 ? 0 : 1); }
	public static long sign(long x) { return (long) (x < 0 ? -1 : x == 0 ? 0 : 1); }
	public static short sign(short x) { return (short) (x < 0 ? -1 : x == 0 ? 0 : 1); }
	public static float sign(float x) { return (float) (x < 0 ? -1.0 : x == 0 ? 0.0 : 1.0); }
	public static double sign(double x) { return (double) (x < 0 ? -1.0 : x == 0 ? 0.0 : 1.0); }
	public static char sign(char x) { return (char) (x < 0 ? -1 : x == 0 ? 0 : 1); }
	public static int[] sign(int[] x, int xOff, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (int) sign(x[xI + 0]); target[targetI + 1] = (int) sign(x[xI + 1]); target[targetI + 2] = (int) sign(x[xI + 2]); target[targetI + 3] = (int) sign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (int) sign(x[xI + 0]); } return target; }
	public static long[] sign(long[] x, int xOff, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (long) sign(x[xI + 0]); target[targetI + 1] = (long) sign(x[xI + 1]); target[targetI + 2] = (long) sign(x[xI + 2]); target[targetI + 3] = (long) sign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (long) sign(x[xI + 0]); } return target; }
	public static short[] sign(short[] x, int xOff, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (short) sign(x[xI + 0]); target[targetI + 1] = (short) sign(x[xI + 1]); target[targetI + 2] = (short) sign(x[xI + 2]); target[targetI + 3] = (short) sign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (short) sign(x[xI + 0]); } return target; }
	public static float[] sign(float[] x, int xOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (float) sign(x[xI + 0]); target[targetI + 1] = (float) sign(x[xI + 1]); target[targetI + 2] = (float) sign(x[xI + 2]); target[targetI + 3] = (float) sign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (float) sign(x[xI + 0]); } return target; }
	public static double[] sign(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) sign(x[xI + 0]); target[targetI + 1] = (double) sign(x[xI + 1]); target[targetI + 2] = (double) sign(x[xI + 2]); target[targetI + 3] = (double) sign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) sign(x[xI + 0]); } return target; }
	public static char[] sign(char[] x, int xOff, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (char) sign(x[xI + 0]); target[targetI + 1] = (char) sign(x[xI + 1]); target[targetI + 2] = (char) sign(x[xI + 2]); target[targetI + 3] = (char) sign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (char) sign(x[xI + 0]); } return target; }
	public static int[] sign(int[] x, int xOff, int[] target, int targetOff) { return sign(x, xOff, target, targetOff, target.length - targetOff); }
	public static long[] sign(long[] x, int xOff, long[] target, int targetOff) { return sign(x, xOff, target, targetOff, target.length - targetOff); }
	public static short[] sign(short[] x, int xOff, short[] target, int targetOff) { return sign(x, xOff, target, targetOff, target.length - targetOff); }
	public static float[] sign(float[] x, int xOff, float[] target, int targetOff) { return sign(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] sign(double[] x, int xOff, double[] target, int targetOff) { return sign(x, xOff, target, targetOff, target.length - targetOff); }
	public static char[] sign(char[] x, int xOff, char[] target, int targetOff) { return sign(x, xOff, target, targetOff, target.length - targetOff); }
	public static int[] sign(int[] x, int[] target) { return sign(x, 0, target, 0); }
	public static long[] sign(long[] x, long[] target) { return sign(x, 0, target, 0); }
	public static short[] sign(short[] x, short[] target) { return sign(x, 0, target, 0); }
	public static float[] sign(float[] x, float[] target) { return sign(x, 0, target, 0); }
	public static double[] sign(double[] x, double[] target) { return sign(x, 0, target, 0); }
	public static char[] sign(char[] x, char[] target) { return sign(x, 0, target, 0); }
	public static int[] sign(int[] x) { return sign(x, new int[(x.length) / 1]); }
	public static long[] sign(long[] x) { return sign(x, new long[(x.length) / 1]); }
	public static short[] sign(short[] x) { return sign(x, new short[(x.length) / 1]); }
	public static float[] sign(float[] x) { return sign(x, new float[(x.length) / 1]); }
	public static double[] sign(double[] x) { return sign(x, new double[(x.length) / 1]); }
	public static char[] sign(char[] x) { return sign(x, new char[(x.length) / 1]); }

	protected static double signD(int x) { return (double) sign(x); }
	protected static double signD(long x) { return (double) sign(x); }
	protected static double signD(short x) { return (double) sign(x); }
	protected static double signD(float x) { return (double) sign(x); }
	protected static double signD(double x) { return (double) sign(x); }
	protected static double signD(char x) { return (double) sign(x); }
	public static double[] signD(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) signD(x[xI + 0]); target[targetI + 1] = (double) signD(x[xI + 1]); target[targetI + 2] = (double) signD(x[xI + 2]); target[targetI + 3] = (double) signD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) signD(x[xI + 0]); } return target; }
	public static double[] signD(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) signD(x[xI + 0]); target[targetI + 1] = (double) signD(x[xI + 1]); target[targetI + 2] = (double) signD(x[xI + 2]); target[targetI + 3] = (double) signD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) signD(x[xI + 0]); } return target; }
	public static double[] signD(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) signD(x[xI + 0]); target[targetI + 1] = (double) signD(x[xI + 1]); target[targetI + 2] = (double) signD(x[xI + 2]); target[targetI + 3] = (double) signD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) signD(x[xI + 0]); } return target; }
	public static double[] signD(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) signD(x[xI + 0]); target[targetI + 1] = (double) signD(x[xI + 1]); target[targetI + 2] = (double) signD(x[xI + 2]); target[targetI + 3] = (double) signD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) signD(x[xI + 0]); } return target; }
	public static double[] signD(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) signD(x[xI + 0]); target[targetI + 1] = (double) signD(x[xI + 1]); target[targetI + 2] = (double) signD(x[xI + 2]); target[targetI + 3] = (double) signD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) signD(x[xI + 0]); } return target; }
	public static double[] signD(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) signD(x[xI + 0]); target[targetI + 1] = (double) signD(x[xI + 1]); target[targetI + 2] = (double) signD(x[xI + 2]); target[targetI + 3] = (double) signD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) signD(x[xI + 0]); } return target; }
	public static double[] signD(int[] x, int xOff, double[] target, int targetOff) { return signD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] signD(long[] x, int xOff, double[] target, int targetOff) { return signD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] signD(short[] x, int xOff, double[] target, int targetOff) { return signD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] signD(float[] x, int xOff, double[] target, int targetOff) { return signD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] signD(double[] x, int xOff, double[] target, int targetOff) { return signD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] signD(char[] x, int xOff, double[] target, int targetOff) { return signD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] signD(int[] x, double[] target) { return signD(x, 0, target, 0); }
	public static double[] signD(long[] x, double[] target) { return signD(x, 0, target, 0); }
	public static double[] signD(short[] x, double[] target) { return signD(x, 0, target, 0); }
	public static double[] signD(float[] x, double[] target) { return signD(x, 0, target, 0); }
	public static double[] signD(double[] x, double[] target) { return signD(x, 0, target, 0); }
	public static double[] signD(char[] x, double[] target) { return signD(x, 0, target, 0); }
	public static double[] signD(int[] x) { return signD(x, new double[(x.length) / 1]); }
	public static double[] signD(long[] x) { return signD(x, new double[(x.length) / 1]); }
	public static double[] signD(short[] x) { return signD(x, new double[(x.length) / 1]); }
	public static double[] signD(float[] x) { return signD(x, new double[(x.length) / 1]); }
	public static double[] signD(double[] x) { return signD(x, new double[(x.length) / 1]); }
	public static double[] signD(char[] x) { return signD(x, new double[(x.length) / 1]); }

	public static int nonZeroSign(int x) { return (int) (x < 0 ? -1 : 1); }
	public static long nonZeroSign(long x) { return (long) (x < 0 ? -1 : 1); }
	public static short nonZeroSign(short x) { return (short) (x < 0 ? -1 : 1); }
	public static float nonZeroSign(float x) { return (float) (x < 0 ? -1.0 : 1.0); }
	public static double nonZeroSign(double x) { return (double) (x < 0 ? -1.0 : 1.0); }
	public static char nonZeroSign(char x) { return (char) (x < 0 ? -1 : 1); }
	public static int[] nonZeroSign(int[] x, int xOff, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (int) nonZeroSign(x[xI + 0]); target[targetI + 1] = (int) nonZeroSign(x[xI + 1]); target[targetI + 2] = (int) nonZeroSign(x[xI + 2]); target[targetI + 3] = (int) nonZeroSign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (int) nonZeroSign(x[xI + 0]); } return target; }
	public static long[] nonZeroSign(long[] x, int xOff, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (long) nonZeroSign(x[xI + 0]); target[targetI + 1] = (long) nonZeroSign(x[xI + 1]); target[targetI + 2] = (long) nonZeroSign(x[xI + 2]); target[targetI + 3] = (long) nonZeroSign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (long) nonZeroSign(x[xI + 0]); } return target; }
	public static short[] nonZeroSign(short[] x, int xOff, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (short) nonZeroSign(x[xI + 0]); target[targetI + 1] = (short) nonZeroSign(x[xI + 1]); target[targetI + 2] = (short) nonZeroSign(x[xI + 2]); target[targetI + 3] = (short) nonZeroSign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (short) nonZeroSign(x[xI + 0]); } return target; }
	public static float[] nonZeroSign(float[] x, int xOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (float) nonZeroSign(x[xI + 0]); target[targetI + 1] = (float) nonZeroSign(x[xI + 1]); target[targetI + 2] = (float) nonZeroSign(x[xI + 2]); target[targetI + 3] = (float) nonZeroSign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (float) nonZeroSign(x[xI + 0]); } return target; }
	public static double[] nonZeroSign(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) nonZeroSign(x[xI + 0]); target[targetI + 1] = (double) nonZeroSign(x[xI + 1]); target[targetI + 2] = (double) nonZeroSign(x[xI + 2]); target[targetI + 3] = (double) nonZeroSign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) nonZeroSign(x[xI + 0]); } return target; }
	public static char[] nonZeroSign(char[] x, int xOff, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (char) nonZeroSign(x[xI + 0]); target[targetI + 1] = (char) nonZeroSign(x[xI + 1]); target[targetI + 2] = (char) nonZeroSign(x[xI + 2]); target[targetI + 3] = (char) nonZeroSign(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (char) nonZeroSign(x[xI + 0]); } return target; }
	public static int[] nonZeroSign(int[] x, int xOff, int[] target, int targetOff) { return nonZeroSign(x, xOff, target, targetOff, target.length - targetOff); }
	public static long[] nonZeroSign(long[] x, int xOff, long[] target, int targetOff) { return nonZeroSign(x, xOff, target, targetOff, target.length - targetOff); }
	public static short[] nonZeroSign(short[] x, int xOff, short[] target, int targetOff) { return nonZeroSign(x, xOff, target, targetOff, target.length - targetOff); }
	public static float[] nonZeroSign(float[] x, int xOff, float[] target, int targetOff) { return nonZeroSign(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] nonZeroSign(double[] x, int xOff, double[] target, int targetOff) { return nonZeroSign(x, xOff, target, targetOff, target.length - targetOff); }
	public static char[] nonZeroSign(char[] x, int xOff, char[] target, int targetOff) { return nonZeroSign(x, xOff, target, targetOff, target.length - targetOff); }
	public static int[] nonZeroSign(int[] x, int[] target) { return nonZeroSign(x, 0, target, 0); }
	public static long[] nonZeroSign(long[] x, long[] target) { return nonZeroSign(x, 0, target, 0); }
	public static short[] nonZeroSign(short[] x, short[] target) { return nonZeroSign(x, 0, target, 0); }
	public static float[] nonZeroSign(float[] x, float[] target) { return nonZeroSign(x, 0, target, 0); }
	public static double[] nonZeroSign(double[] x, double[] target) { return nonZeroSign(x, 0, target, 0); }
	public static char[] nonZeroSign(char[] x, char[] target) { return nonZeroSign(x, 0, target, 0); }
	public static int[] nonZeroSign(int[] x) { return nonZeroSign(x, new int[(x.length) / 1]); }
	public static long[] nonZeroSign(long[] x) { return nonZeroSign(x, new long[(x.length) / 1]); }
	public static short[] nonZeroSign(short[] x) { return nonZeroSign(x, new short[(x.length) / 1]); }
	public static float[] nonZeroSign(float[] x) { return nonZeroSign(x, new float[(x.length) / 1]); }
	public static double[] nonZeroSign(double[] x) { return nonZeroSign(x, new double[(x.length) / 1]); }
	public static char[] nonZeroSign(char[] x) { return nonZeroSign(x, new char[(x.length) / 1]); }

	protected static double nonZeroSignD(int x) { return (double) nonZeroSign(x); }
	protected static double nonZeroSignD(long x) { return (double) nonZeroSign(x); }
	protected static double nonZeroSignD(short x) { return (double) nonZeroSign(x); }
	protected static double nonZeroSignD(float x) { return (double) nonZeroSign(x); }
	protected static double nonZeroSignD(double x) { return (double) nonZeroSign(x); }
	protected static double nonZeroSignD(char x) { return (double) nonZeroSign(x); }
	public static double[] nonZeroSignD(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); target[targetI + 1] = (double) nonZeroSignD(x[xI + 1]); target[targetI + 2] = (double) nonZeroSignD(x[xI + 2]); target[targetI + 3] = (double) nonZeroSignD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); } return target; }
	public static double[] nonZeroSignD(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); target[targetI + 1] = (double) nonZeroSignD(x[xI + 1]); target[targetI + 2] = (double) nonZeroSignD(x[xI + 2]); target[targetI + 3] = (double) nonZeroSignD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); } return target; }
	public static double[] nonZeroSignD(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); target[targetI + 1] = (double) nonZeroSignD(x[xI + 1]); target[targetI + 2] = (double) nonZeroSignD(x[xI + 2]); target[targetI + 3] = (double) nonZeroSignD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); } return target; }
	public static double[] nonZeroSignD(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); target[targetI + 1] = (double) nonZeroSignD(x[xI + 1]); target[targetI + 2] = (double) nonZeroSignD(x[xI + 2]); target[targetI + 3] = (double) nonZeroSignD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); } return target; }
	public static double[] nonZeroSignD(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); target[targetI + 1] = (double) nonZeroSignD(x[xI + 1]); target[targetI + 2] = (double) nonZeroSignD(x[xI + 2]); target[targetI + 3] = (double) nonZeroSignD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); } return target; }
	public static double[] nonZeroSignD(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); target[targetI + 1] = (double) nonZeroSignD(x[xI + 1]); target[targetI + 2] = (double) nonZeroSignD(x[xI + 2]); target[targetI + 3] = (double) nonZeroSignD(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) nonZeroSignD(x[xI + 0]); } return target; }
	public static double[] nonZeroSignD(int[] x, int xOff, double[] target, int targetOff) { return nonZeroSignD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] nonZeroSignD(long[] x, int xOff, double[] target, int targetOff) { return nonZeroSignD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] nonZeroSignD(short[] x, int xOff, double[] target, int targetOff) { return nonZeroSignD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] nonZeroSignD(float[] x, int xOff, double[] target, int targetOff) { return nonZeroSignD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] nonZeroSignD(double[] x, int xOff, double[] target, int targetOff) { return nonZeroSignD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] nonZeroSignD(char[] x, int xOff, double[] target, int targetOff) { return nonZeroSignD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] nonZeroSignD(int[] x, double[] target) { return nonZeroSignD(x, 0, target, 0); }
	public static double[] nonZeroSignD(long[] x, double[] target) { return nonZeroSignD(x, 0, target, 0); }
	public static double[] nonZeroSignD(short[] x, double[] target) { return nonZeroSignD(x, 0, target, 0); }
	public static double[] nonZeroSignD(float[] x, double[] target) { return nonZeroSignD(x, 0, target, 0); }
	public static double[] nonZeroSignD(double[] x, double[] target) { return nonZeroSignD(x, 0, target, 0); }
	public static double[] nonZeroSignD(char[] x, double[] target) { return nonZeroSignD(x, 0, target, 0); }
	public static double[] nonZeroSignD(int[] x) { return nonZeroSignD(x, new double[(x.length) / 1]); }
	public static double[] nonZeroSignD(long[] x) { return nonZeroSignD(x, new double[(x.length) / 1]); }
	public static double[] nonZeroSignD(short[] x) { return nonZeroSignD(x, new double[(x.length) / 1]); }
	public static double[] nonZeroSignD(float[] x) { return nonZeroSignD(x, new double[(x.length) / 1]); }
	public static double[] nonZeroSignD(double[] x) { return nonZeroSignD(x, new double[(x.length) / 1]); }
	public static double[] nonZeroSignD(char[] x) { return nonZeroSignD(x, new double[(x.length) / 1]); }

	// floor
	public static double floor(double x) { return Math.floor(x); }
	public static int[] floor(int[] x, int xOff, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (int) (Math.floor(x[xI + 0])); target[targetI + 1] = (int) (Math.floor(x[xI + 1])); target[targetI + 2] = (int) (Math.floor(x[xI + 2])); target[targetI + 3] = (int) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (int) (Math.floor(x[xI + 0])); } return target; }
	public static long[] floor(long[] x, int xOff, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (long) (Math.floor(x[xI + 0])); target[targetI + 1] = (long) (Math.floor(x[xI + 1])); target[targetI + 2] = (long) (Math.floor(x[xI + 2])); target[targetI + 3] = (long) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (long) (Math.floor(x[xI + 0])); } return target; }
	public static short[] floor(short[] x, int xOff, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (short) (Math.floor(x[xI + 0])); target[targetI + 1] = (short) (Math.floor(x[xI + 1])); target[targetI + 2] = (short) (Math.floor(x[xI + 2])); target[targetI + 3] = (short) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (short) (Math.floor(x[xI + 0])); } return target; }
	public static float[] floor(float[] x, int xOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (float) (Math.floor(x[xI + 0])); target[targetI + 1] = (float) (Math.floor(x[xI + 1])); target[targetI + 2] = (float) (Math.floor(x[xI + 2])); target[targetI + 3] = (float) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (float) (Math.floor(x[xI + 0])); } return target; }
	public static double[] floor(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.floor(x[xI + 0])); target[targetI + 1] = (double) (Math.floor(x[xI + 1])); target[targetI + 2] = (double) (Math.floor(x[xI + 2])); target[targetI + 3] = (double) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.floor(x[xI + 0])); } return target; }
	public static char[] floor(char[] x, int xOff, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (char) (Math.floor(x[xI + 0])); target[targetI + 1] = (char) (Math.floor(x[xI + 1])); target[targetI + 2] = (char) (Math.floor(x[xI + 2])); target[targetI + 3] = (char) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (char) (Math.floor(x[xI + 0])); } return target; }
	public static int[] floor(int[] x, int xOff, int[] target, int targetOff) { return floor(x, xOff, target, targetOff, target.length - targetOff); }
	public static long[] floor(long[] x, int xOff, long[] target, int targetOff) { return floor(x, xOff, target, targetOff, target.length - targetOff); }
	public static short[] floor(short[] x, int xOff, short[] target, int targetOff) { return floor(x, xOff, target, targetOff, target.length - targetOff); }
	public static float[] floor(float[] x, int xOff, float[] target, int targetOff) { return floor(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] floor(double[] x, int xOff, double[] target, int targetOff) { return floor(x, xOff, target, targetOff, target.length - targetOff); }
	public static char[] floor(char[] x, int xOff, char[] target, int targetOff) { return floor(x, xOff, target, targetOff, target.length - targetOff); }
	public static int[] floor(int[] x, int[] target) { return floor(x, 0, target, 0); }
	public static long[] floor(long[] x, long[] target) { return floor(x, 0, target, 0); }
	public static short[] floor(short[] x, short[] target) { return floor(x, 0, target, 0); }
	public static float[] floor(float[] x, float[] target) { return floor(x, 0, target, 0); }
	public static double[] floor(double[] x, double[] target) { return floor(x, 0, target, 0); }
	public static char[] floor(char[] x, char[] target) { return floor(x, 0, target, 0); }
	public static int[] floor(int[] x) { return floor(x, new int[(x.length) / 1]); }
	public static long[] floor(long[] x) { return floor(x, new long[(x.length) / 1]); }
	public static short[] floor(short[] x) { return floor(x, new short[(x.length) / 1]); }
	public static float[] floor(float[] x) { return floor(x, new float[(x.length) / 1]); }
	public static double[] floor(double[] x) { return floor(x, new double[(x.length) / 1]); }
	public static char[] floor(char[] x) { return floor(x, new char[(x.length) / 1]); }

	protected static double floorD(double x) { return (double) Math.floor(x); }
	public static double[] floorD(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.floor(x[xI + 0])); target[targetI + 1] = (double) (Math.floor(x[xI + 1])); target[targetI + 2] = (double) (Math.floor(x[xI + 2])); target[targetI + 3] = (double) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.floor(x[xI + 0])); } return target; }
	public static double[] floorD(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.floor(x[xI + 0])); target[targetI + 1] = (double) (Math.floor(x[xI + 1])); target[targetI + 2] = (double) (Math.floor(x[xI + 2])); target[targetI + 3] = (double) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.floor(x[xI + 0])); } return target; }
	public static double[] floorD(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.floor(x[xI + 0])); target[targetI + 1] = (double) (Math.floor(x[xI + 1])); target[targetI + 2] = (double) (Math.floor(x[xI + 2])); target[targetI + 3] = (double) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.floor(x[xI + 0])); } return target; }
	public static double[] floorD(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.floor(x[xI + 0])); target[targetI + 1] = (double) (Math.floor(x[xI + 1])); target[targetI + 2] = (double) (Math.floor(x[xI + 2])); target[targetI + 3] = (double) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.floor(x[xI + 0])); } return target; }
	public static double[] floorD(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.floor(x[xI + 0])); target[targetI + 1] = (double) (Math.floor(x[xI + 1])); target[targetI + 2] = (double) (Math.floor(x[xI + 2])); target[targetI + 3] = (double) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.floor(x[xI + 0])); } return target; }
	public static double[] floorD(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.floor(x[xI + 0])); target[targetI + 1] = (double) (Math.floor(x[xI + 1])); target[targetI + 2] = (double) (Math.floor(x[xI + 2])); target[targetI + 3] = (double) (Math.floor(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.floor(x[xI + 0])); } return target; }
	public static double[] floorD(int[] x, int xOff, double[] target, int targetOff) { return floorD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] floorD(long[] x, int xOff, double[] target, int targetOff) { return floorD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] floorD(short[] x, int xOff, double[] target, int targetOff) { return floorD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] floorD(float[] x, int xOff, double[] target, int targetOff) { return floorD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] floorD(double[] x, int xOff, double[] target, int targetOff) { return floorD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] floorD(char[] x, int xOff, double[] target, int targetOff) { return floorD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] floorD(int[] x, double[] target) { return floorD(x, 0, target, 0); }
	public static double[] floorD(long[] x, double[] target) { return floorD(x, 0, target, 0); }
	public static double[] floorD(short[] x, double[] target) { return floorD(x, 0, target, 0); }
	public static double[] floorD(float[] x, double[] target) { return floorD(x, 0, target, 0); }
	public static double[] floorD(double[] x, double[] target) { return floorD(x, 0, target, 0); }
	public static double[] floorD(char[] x, double[] target) { return floorD(x, 0, target, 0); }
	public static double[] floorD(int[] x) { return floorD(x, new double[(x.length) / 1]); }
	public static double[] floorD(long[] x) { return floorD(x, new double[(x.length) / 1]); }
	public static double[] floorD(short[] x) { return floorD(x, new double[(x.length) / 1]); }
	public static double[] floorD(float[] x) { return floorD(x, new double[(x.length) / 1]); }
	public static double[] floorD(double[] x) { return floorD(x, new double[(x.length) / 1]); }
	public static double[] floorD(char[] x) { return floorD(x, new double[(x.length) / 1]); }

	// ceil
	public static double ceil(double x) { return Math.ceil(x); }
	public static int[] ceil(int[] x, int xOff, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (int) (Math.ceil(x[xI + 0])); target[targetI + 1] = (int) (Math.ceil(x[xI + 1])); target[targetI + 2] = (int) (Math.ceil(x[xI + 2])); target[targetI + 3] = (int) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (int) (Math.ceil(x[xI + 0])); } return target; }
	public static long[] ceil(long[] x, int xOff, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (long) (Math.ceil(x[xI + 0])); target[targetI + 1] = (long) (Math.ceil(x[xI + 1])); target[targetI + 2] = (long) (Math.ceil(x[xI + 2])); target[targetI + 3] = (long) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (long) (Math.ceil(x[xI + 0])); } return target; }
	public static short[] ceil(short[] x, int xOff, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (short) (Math.ceil(x[xI + 0])); target[targetI + 1] = (short) (Math.ceil(x[xI + 1])); target[targetI + 2] = (short) (Math.ceil(x[xI + 2])); target[targetI + 3] = (short) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (short) (Math.ceil(x[xI + 0])); } return target; }
	public static float[] ceil(float[] x, int xOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (float) (Math.ceil(x[xI + 0])); target[targetI + 1] = (float) (Math.ceil(x[xI + 1])); target[targetI + 2] = (float) (Math.ceil(x[xI + 2])); target[targetI + 3] = (float) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (float) (Math.ceil(x[xI + 0])); } return target; }
	public static double[] ceil(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); target[targetI + 1] = (double) (Math.ceil(x[xI + 1])); target[targetI + 2] = (double) (Math.ceil(x[xI + 2])); target[targetI + 3] = (double) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); } return target; }
	public static char[] ceil(char[] x, int xOff, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (char) (Math.ceil(x[xI + 0])); target[targetI + 1] = (char) (Math.ceil(x[xI + 1])); target[targetI + 2] = (char) (Math.ceil(x[xI + 2])); target[targetI + 3] = (char) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (char) (Math.ceil(x[xI + 0])); } return target; }
	public static int[] ceil(int[] x, int xOff, int[] target, int targetOff) { return ceil(x, xOff, target, targetOff, target.length - targetOff); }
	public static long[] ceil(long[] x, int xOff, long[] target, int targetOff) { return ceil(x, xOff, target, targetOff, target.length - targetOff); }
	public static short[] ceil(short[] x, int xOff, short[] target, int targetOff) { return ceil(x, xOff, target, targetOff, target.length - targetOff); }
	public static float[] ceil(float[] x, int xOff, float[] target, int targetOff) { return ceil(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] ceil(double[] x, int xOff, double[] target, int targetOff) { return ceil(x, xOff, target, targetOff, target.length - targetOff); }
	public static char[] ceil(char[] x, int xOff, char[] target, int targetOff) { return ceil(x, xOff, target, targetOff, target.length - targetOff); }
	public static int[] ceil(int[] x, int[] target) { return ceil(x, 0, target, 0); }
	public static long[] ceil(long[] x, long[] target) { return ceil(x, 0, target, 0); }
	public static short[] ceil(short[] x, short[] target) { return ceil(x, 0, target, 0); }
	public static float[] ceil(float[] x, float[] target) { return ceil(x, 0, target, 0); }
	public static double[] ceil(double[] x, double[] target) { return ceil(x, 0, target, 0); }
	public static char[] ceil(char[] x, char[] target) { return ceil(x, 0, target, 0); }
	public static int[] ceil(int[] x) { return ceil(x, new int[(x.length) / 1]); }
	public static long[] ceil(long[] x) { return ceil(x, new long[(x.length) / 1]); }
	public static short[] ceil(short[] x) { return ceil(x, new short[(x.length) / 1]); }
	public static float[] ceil(float[] x) { return ceil(x, new float[(x.length) / 1]); }
	public static double[] ceil(double[] x) { return ceil(x, new double[(x.length) / 1]); }
	public static char[] ceil(char[] x) { return ceil(x, new char[(x.length) / 1]); }

	protected static double ceilD(double x) { return (double) Math.ceil(x); }
	public static double[] ceilD(int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); target[targetI + 1] = (double) (Math.ceil(x[xI + 1])); target[targetI + 2] = (double) (Math.ceil(x[xI + 2])); target[targetI + 3] = (double) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); } return target; }
	public static double[] ceilD(long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); target[targetI + 1] = (double) (Math.ceil(x[xI + 1])); target[targetI + 2] = (double) (Math.ceil(x[xI + 2])); target[targetI + 3] = (double) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); } return target; }
	public static double[] ceilD(short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); target[targetI + 1] = (double) (Math.ceil(x[xI + 1])); target[targetI + 2] = (double) (Math.ceil(x[xI + 2])); target[targetI + 3] = (double) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); } return target; }
	public static double[] ceilD(float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); target[targetI + 1] = (double) (Math.ceil(x[xI + 1])); target[targetI + 2] = (double) (Math.ceil(x[xI + 2])); target[targetI + 3] = (double) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); } return target; }
	public static double[] ceilD(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); target[targetI + 1] = (double) (Math.ceil(x[xI + 1])); target[targetI + 2] = (double) (Math.ceil(x[xI + 2])); target[targetI + 3] = (double) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); } return target; }
	public static double[] ceilD(char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); target[targetI + 1] = (double) (Math.ceil(x[xI + 1])); target[targetI + 2] = (double) (Math.ceil(x[xI + 2])); target[targetI + 3] = (double) (Math.ceil(x[xI + 3])); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.ceil(x[xI + 0])); } return target; }
	public static double[] ceilD(int[] x, int xOff, double[] target, int targetOff) { return ceilD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] ceilD(long[] x, int xOff, double[] target, int targetOff) { return ceilD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] ceilD(short[] x, int xOff, double[] target, int targetOff) { return ceilD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] ceilD(float[] x, int xOff, double[] target, int targetOff) { return ceilD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] ceilD(double[] x, int xOff, double[] target, int targetOff) { return ceilD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] ceilD(char[] x, int xOff, double[] target, int targetOff) { return ceilD(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] ceilD(int[] x, double[] target) { return ceilD(x, 0, target, 0); }
	public static double[] ceilD(long[] x, double[] target) { return ceilD(x, 0, target, 0); }
	public static double[] ceilD(short[] x, double[] target) { return ceilD(x, 0, target, 0); }
	public static double[] ceilD(float[] x, double[] target) { return ceilD(x, 0, target, 0); }
	public static double[] ceilD(double[] x, double[] target) { return ceilD(x, 0, target, 0); }
	public static double[] ceilD(char[] x, double[] target) { return ceilD(x, 0, target, 0); }
	public static double[] ceilD(int[] x) { return ceilD(x, new double[(x.length) / 1]); }
	public static double[] ceilD(long[] x) { return ceilD(x, new double[(x.length) / 1]); }
	public static double[] ceilD(short[] x) { return ceilD(x, new double[(x.length) / 1]); }
	public static double[] ceilD(float[] x) { return ceilD(x, new double[(x.length) / 1]); }
	public static double[] ceilD(double[] x) { return ceilD(x, new double[(x.length) / 1]); }
	public static double[] ceilD(char[] x) { return ceilD(x, new double[(x.length) / 1]); }

	// fract
	public static double fract(double x) { return x - Math.floor(x); }
	public static float[] fract(float[] x, int xOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (float) fract(x[xI + 0]); target[targetI + 1] = (float) fract(x[xI + 1]); target[targetI + 2] = (float) fract(x[xI + 2]); target[targetI + 3] = (float) fract(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (float) fract(x[xI + 0]); } return target; }
	public static double[] fract(double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) fract(x[xI + 0]); target[targetI + 1] = (double) fract(x[xI + 1]); target[targetI + 2] = (double) fract(x[xI + 2]); target[targetI + 3] = (double) fract(x[xI + 3]); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) fract(x[xI + 0]); } return target; }
	public static float[] fract(float[] x, int xOff, float[] target, int targetOff) { return fract(x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] fract(double[] x, int xOff, double[] target, int targetOff) { return fract(x, xOff, target, targetOff, target.length - targetOff); }
	public static float[] fract(float[] x, float[] target) { return fract(x, 0, target, 0); }
	public static double[] fract(double[] x, double[] target) { return fract(x, 0, target, 0); }
	public static float[] fract(float[] x) { return fract(x, new float[(x.length) / 1]); }
	public static double[] fract(double[] x) { return fract(x, new double[(x.length) / 1]); }

	// min
	public static int min(int x, int y) { return Math.min(x, y); }
	public static long min(long x, long y) { return Math.min(x, y); }
	public static short min(short x, short y) { return (short) Math.min(x, y); }
	public static float min(float x, float y) { return Math.min(x, y); }
	public static double min(double x, double y) { return Math.min(x, y); }
	public static char min(char x, char y) { return (char) Math.min(x, y); }
	public static int[] min(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (int) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (int) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (int) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (int) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (int) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static long[] min(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (long) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (long) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (long) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (long) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (long) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static short[] min(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (short) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (short) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (short) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (short) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (short) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static float[] min(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (float) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (float) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (float) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (float) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (float) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] min(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static char[] min(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (char) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (char) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (char) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (char) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (char) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static int[] min(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff) { return min(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static long[] min(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff) { return min(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static short[] min(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff) { return min(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static float[] min(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff) { return min(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] min(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff) { return min(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static char[] min(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff) { return min(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static int[] min(int[] x, int[] y, int[] target) { return min(x, 0, y, 0, target, 0); }
	public static long[] min(long[] x, long[] y, long[] target) { return min(x, 0, y, 0, target, 0); }
	public static short[] min(short[] x, short[] y, short[] target) { return min(x, 0, y, 0, target, 0); }
	public static float[] min(float[] x, float[] y, float[] target) { return min(x, 0, y, 0, target, 0); }
	public static double[] min(double[] x, double[] y, double[] target) { return min(x, 0, y, 0, target, 0); }
	public static char[] min(char[] x, char[] y, char[] target) { return min(x, 0, y, 0, target, 0); }
	public static int[] min(int[] x, int[] y) { return min(x, y, new int[(x.length + y.length) / 2]); }
	public static long[] min(long[] x, long[] y) { return min(x, y, new long[(x.length + y.length) / 2]); }
	public static short[] min(short[] x, short[] y) { return min(x, y, new short[(x.length + y.length) / 2]); }
	public static float[] min(float[] x, float[] y) { return min(x, y, new float[(x.length + y.length) / 2]); }
	public static double[] min(double[] x, double[] y) { return min(x, y, new double[(x.length + y.length) / 2]); }
	public static char[] min(char[] x, char[] y) { return min(x, y, new char[(x.length + y.length) / 2]); }

	public static int[] min(int[] x, int xOff, int y, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (int) (Math.min(x[xI + 0], y)); target[targetI + 1] = (int) (Math.min(x[xI + 1], y)); target[targetI + 2] = (int) (Math.min(x[xI + 2], y)); target[targetI + 3] = (int) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (int) (Math.min(x[xI + 0], y)); } return target; }
	public static long[] min(long[] x, int xOff, long y, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (long) (Math.min(x[xI + 0], y)); target[targetI + 1] = (long) (Math.min(x[xI + 1], y)); target[targetI + 2] = (long) (Math.min(x[xI + 2], y)); target[targetI + 3] = (long) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (long) (Math.min(x[xI + 0], y)); } return target; }
	public static short[] min(short[] x, int xOff, short y, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (short) (Math.min(x[xI + 0], y)); target[targetI + 1] = (short) (Math.min(x[xI + 1], y)); target[targetI + 2] = (short) (Math.min(x[xI + 2], y)); target[targetI + 3] = (short) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (short) (Math.min(x[xI + 0], y)); } return target; }
	public static float[] min(float[] x, int xOff, float y, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (float) (Math.min(x[xI + 0], y)); target[targetI + 1] = (float) (Math.min(x[xI + 1], y)); target[targetI + 2] = (float) (Math.min(x[xI + 2], y)); target[targetI + 3] = (float) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (float) (Math.min(x[xI + 0], y)); } return target; }
	public static double[] min(double[] x, int xOff, double y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); target[targetI + 1] = (double) (Math.min(x[xI + 1], y)); target[targetI + 2] = (double) (Math.min(x[xI + 2], y)); target[targetI + 3] = (double) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); } return target; }
	public static char[] min(char[] x, int xOff, char y, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (char) (Math.min(x[xI + 0], y)); target[targetI + 1] = (char) (Math.min(x[xI + 1], y)); target[targetI + 2] = (char) (Math.min(x[xI + 2], y)); target[targetI + 3] = (char) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (char) (Math.min(x[xI + 0], y)); } return target; }
	public static int[] min(int[] x, int xOff, int y, int[] target, int targetOff) { return min(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static long[] min(long[] x, int xOff, long y, long[] target, int targetOff) { return min(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static short[] min(short[] x, int xOff, short y, short[] target, int targetOff) { return min(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static float[] min(float[] x, int xOff, float y, float[] target, int targetOff) { return min(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] min(double[] x, int xOff, double y, double[] target, int targetOff) { return min(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static char[] min(char[] x, int xOff, char y, char[] target, int targetOff) { return min(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static int[] min(int[] x, int y, int[] target) { return min(x, 0, y, target, 0); }
	public static long[] min(long[] x, long y, long[] target) { return min(x, 0, y, target, 0); }
	public static short[] min(short[] x, short y, short[] target) { return min(x, 0, y, target, 0); }
	public static float[] min(float[] x, float y, float[] target) { return min(x, 0, y, target, 0); }
	public static double[] min(double[] x, double y, double[] target) { return min(x, 0, y, target, 0); }
	public static char[] min(char[] x, char y, char[] target) { return min(x, 0, y, target, 0); }
	public static int[] min(int[] x, int y) { return min(x, y, new int[(x.length) / 1]); }
	public static long[] min(long[] x, long y) { return min(x, y, new long[(x.length) / 1]); }
	public static short[] min(short[] x, short y) { return min(x, y, new short[(x.length) / 1]); }
	public static float[] min(float[] x, float y) { return min(x, y, new float[(x.length) / 1]); }
	public static double[] min(double[] x, double y) { return min(x, y, new double[(x.length) / 1]); }
	public static char[] min(char[] x, char y) { return min(x, y, new char[(x.length) / 1]); }

	protected static double minD(int x, int y) { return (double) Math.min(x, y); }
	protected static double minD(long x, long y) { return (double) Math.min(x, y); }
	protected static double minD(short x, short y) { return (double) Math.min(x, y); }
	protected static double minD(float x, float y) { return (double) Math.min(x, y); }
	protected static double minD(double x, double y) { return (double) Math.min(x, y); }
	protected static double minD(char x, char y) { return (double) Math.min(x, y); }
	public static double[] minD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] minD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] minD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] minD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] minD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] minD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.min(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.min(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.min(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] minD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff) { return minD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] minD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff) { return minD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] minD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff) { return minD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] minD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff) { return minD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] minD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff) { return minD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] minD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff) { return minD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] minD(int[] x, int[] y, double[] target) { return minD(x, 0, y, 0, target, 0); }
	public static double[] minD(long[] x, long[] y, double[] target) { return minD(x, 0, y, 0, target, 0); }
	public static double[] minD(short[] x, short[] y, double[] target) { return minD(x, 0, y, 0, target, 0); }
	public static double[] minD(float[] x, float[] y, double[] target) { return minD(x, 0, y, 0, target, 0); }
	public static double[] minD(double[] x, double[] y, double[] target) { return minD(x, 0, y, 0, target, 0); }
	public static double[] minD(char[] x, char[] y, double[] target) { return minD(x, 0, y, 0, target, 0); }
	public static double[] minD(int[] x, int[] y) { return minD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] minD(long[] x, long[] y) { return minD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] minD(short[] x, short[] y) { return minD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] minD(float[] x, float[] y) { return minD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] minD(double[] x, double[] y) { return minD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] minD(char[] x, char[] y) { return minD(x, y, new double[(x.length + y.length) / 2]); }

	public static double[] minD(int[] x, int xOff, int y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); target[targetI + 1] = (double) (Math.min(x[xI + 1], y)); target[targetI + 2] = (double) (Math.min(x[xI + 2], y)); target[targetI + 3] = (double) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); } return target; }
	public static double[] minD(long[] x, int xOff, long y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); target[targetI + 1] = (double) (Math.min(x[xI + 1], y)); target[targetI + 2] = (double) (Math.min(x[xI + 2], y)); target[targetI + 3] = (double) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); } return target; }
	public static double[] minD(short[] x, int xOff, short y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); target[targetI + 1] = (double) (Math.min(x[xI + 1], y)); target[targetI + 2] = (double) (Math.min(x[xI + 2], y)); target[targetI + 3] = (double) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); } return target; }
	public static double[] minD(float[] x, int xOff, float y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); target[targetI + 1] = (double) (Math.min(x[xI + 1], y)); target[targetI + 2] = (double) (Math.min(x[xI + 2], y)); target[targetI + 3] = (double) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); } return target; }
	public static double[] minD(double[] x, int xOff, double y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); target[targetI + 1] = (double) (Math.min(x[xI + 1], y)); target[targetI + 2] = (double) (Math.min(x[xI + 2], y)); target[targetI + 3] = (double) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); } return target; }
	public static double[] minD(char[] x, int xOff, char y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); target[targetI + 1] = (double) (Math.min(x[xI + 1], y)); target[targetI + 2] = (double) (Math.min(x[xI + 2], y)); target[targetI + 3] = (double) (Math.min(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.min(x[xI + 0], y)); } return target; }
	public static double[] minD(int[] x, int xOff, int y, double[] target, int targetOff) { return minD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] minD(long[] x, int xOff, long y, double[] target, int targetOff) { return minD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] minD(short[] x, int xOff, short y, double[] target, int targetOff) { return minD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] minD(float[] x, int xOff, float y, double[] target, int targetOff) { return minD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] minD(double[] x, int xOff, double y, double[] target, int targetOff) { return minD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] minD(char[] x, int xOff, char y, double[] target, int targetOff) { return minD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] minD(int[] x, int y, double[] target) { return minD(x, 0, y, target, 0); }
	public static double[] minD(long[] x, long y, double[] target) { return minD(x, 0, y, target, 0); }
	public static double[] minD(short[] x, short y, double[] target) { return minD(x, 0, y, target, 0); }
	public static double[] minD(float[] x, float y, double[] target) { return minD(x, 0, y, target, 0); }
	public static double[] minD(double[] x, double y, double[] target) { return minD(x, 0, y, target, 0); }
	public static double[] minD(char[] x, char y, double[] target) { return minD(x, 0, y, target, 0); }
	public static double[] minD(int[] x, int y) { return minD(x, y, new double[(x.length) / 1]); }
	public static double[] minD(long[] x, long y) { return minD(x, y, new double[(x.length) / 1]); }
	public static double[] minD(short[] x, short y) { return minD(x, y, new double[(x.length) / 1]); }
	public static double[] minD(float[] x, float y) { return minD(x, y, new double[(x.length) / 1]); }
	public static double[] minD(double[] x, double y) { return minD(x, y, new double[(x.length) / 1]); }
	public static double[] minD(char[] x, char y) { return minD(x, y, new double[(x.length) / 1]); }

	// max
	public static int max(int x, int y) { return Math.max(x, y); }
	public static long max(long x, long y) { return Math.max(x, y); }
	public static short max(short x, short y) { return (short) Math.max(x, y); }
	public static float max(float x, float y) { return Math.max(x, y); }
	public static double max(double x, double y) { return Math.max(x, y); }
	public static char max(char x, char y) { return (char) Math.max(x, y); }
	public static int[] max(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (int) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (int) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (int) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (int) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (int) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static long[] max(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (long) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (long) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (long) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (long) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (long) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static short[] max(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (short) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (short) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (short) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (short) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (short) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static float[] max(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (float) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (float) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (float) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (float) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (float) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] max(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static char[] max(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (char) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (char) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (char) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (char) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (char) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static int[] max(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff) { return max(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static long[] max(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff) { return max(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static short[] max(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff) { return max(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static float[] max(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff) { return max(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] max(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff) { return max(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static char[] max(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff) { return max(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static int[] max(int[] x, int[] y, int[] target) { return max(x, 0, y, 0, target, 0); }
	public static long[] max(long[] x, long[] y, long[] target) { return max(x, 0, y, 0, target, 0); }
	public static short[] max(short[] x, short[] y, short[] target) { return max(x, 0, y, 0, target, 0); }
	public static float[] max(float[] x, float[] y, float[] target) { return max(x, 0, y, 0, target, 0); }
	public static double[] max(double[] x, double[] y, double[] target) { return max(x, 0, y, 0, target, 0); }
	public static char[] max(char[] x, char[] y, char[] target) { return max(x, 0, y, 0, target, 0); }
	public static int[] max(int[] x, int[] y) { return max(x, y, new int[(x.length + y.length) / 2]); }
	public static long[] max(long[] x, long[] y) { return max(x, y, new long[(x.length + y.length) / 2]); }
	public static short[] max(short[] x, short[] y) { return max(x, y, new short[(x.length + y.length) / 2]); }
	public static float[] max(float[] x, float[] y) { return max(x, y, new float[(x.length + y.length) / 2]); }
	public static double[] max(double[] x, double[] y) { return max(x, y, new double[(x.length + y.length) / 2]); }
	public static char[] max(char[] x, char[] y) { return max(x, y, new char[(x.length + y.length) / 2]); }

	public static int[] max(int[] x, int xOff, int y, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (int) (Math.max(x[xI + 0], y)); target[targetI + 1] = (int) (Math.max(x[xI + 1], y)); target[targetI + 2] = (int) (Math.max(x[xI + 2], y)); target[targetI + 3] = (int) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (int) (Math.max(x[xI + 0], y)); } return target; }
	public static long[] max(long[] x, int xOff, long y, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (long) (Math.max(x[xI + 0], y)); target[targetI + 1] = (long) (Math.max(x[xI + 1], y)); target[targetI + 2] = (long) (Math.max(x[xI + 2], y)); target[targetI + 3] = (long) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (long) (Math.max(x[xI + 0], y)); } return target; }
	public static short[] max(short[] x, int xOff, short y, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (short) (Math.max(x[xI + 0], y)); target[targetI + 1] = (short) (Math.max(x[xI + 1], y)); target[targetI + 2] = (short) (Math.max(x[xI + 2], y)); target[targetI + 3] = (short) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (short) (Math.max(x[xI + 0], y)); } return target; }
	public static float[] max(float[] x, int xOff, float y, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (float) (Math.max(x[xI + 0], y)); target[targetI + 1] = (float) (Math.max(x[xI + 1], y)); target[targetI + 2] = (float) (Math.max(x[xI + 2], y)); target[targetI + 3] = (float) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (float) (Math.max(x[xI + 0], y)); } return target; }
	public static double[] max(double[] x, int xOff, double y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); target[targetI + 1] = (double) (Math.max(x[xI + 1], y)); target[targetI + 2] = (double) (Math.max(x[xI + 2], y)); target[targetI + 3] = (double) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); } return target; }
	public static char[] max(char[] x, int xOff, char y, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (char) (Math.max(x[xI + 0], y)); target[targetI + 1] = (char) (Math.max(x[xI + 1], y)); target[targetI + 2] = (char) (Math.max(x[xI + 2], y)); target[targetI + 3] = (char) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (char) (Math.max(x[xI + 0], y)); } return target; }
	public static int[] max(int[] x, int xOff, int y, int[] target, int targetOff) { return max(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static long[] max(long[] x, int xOff, long y, long[] target, int targetOff) { return max(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static short[] max(short[] x, int xOff, short y, short[] target, int targetOff) { return max(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static float[] max(float[] x, int xOff, float y, float[] target, int targetOff) { return max(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] max(double[] x, int xOff, double y, double[] target, int targetOff) { return max(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static char[] max(char[] x, int xOff, char y, char[] target, int targetOff) { return max(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static int[] max(int[] x, int y, int[] target) { return max(x, 0, y, target, 0); }
	public static long[] max(long[] x, long y, long[] target) { return max(x, 0, y, target, 0); }
	public static short[] max(short[] x, short y, short[] target) { return max(x, 0, y, target, 0); }
	public static float[] max(float[] x, float y, float[] target) { return max(x, 0, y, target, 0); }
	public static double[] max(double[] x, double y, double[] target) { return max(x, 0, y, target, 0); }
	public static char[] max(char[] x, char y, char[] target) { return max(x, 0, y, target, 0); }
	public static int[] max(int[] x, int y) { return max(x, y, new int[(x.length) / 1]); }
	public static long[] max(long[] x, long y) { return max(x, y, new long[(x.length) / 1]); }
	public static short[] max(short[] x, short y) { return max(x, y, new short[(x.length) / 1]); }
	public static float[] max(float[] x, float y) { return max(x, y, new float[(x.length) / 1]); }
	public static double[] max(double[] x, double y) { return max(x, y, new double[(x.length) / 1]); }
	public static char[] max(char[] x, char y) { return max(x, y, new char[(x.length) / 1]); }

	protected static double maxD(int x, int y) { return (double) Math.max(x, y); }
	protected static double maxD(long x, long y) { return (double) Math.max(x, y); }
	protected static double maxD(short x, short y) { return (double) Math.max(x, y); }
	protected static double maxD(float x, float y) { return (double) Math.max(x, y); }
	protected static double maxD(double x, double y) { return (double) Math.max(x, y); }
	protected static double maxD(char x, char y) { return (double) Math.max(x, y); }
	public static double[] maxD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] maxD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] maxD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] maxD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] maxD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] maxD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); target[targetI + 1] = (double) (Math.max(x[xI + 1], y[yI + 1])); target[targetI + 2] = (double) (Math.max(x[xI + 2], y[yI + 2])); target[targetI + 3] = (double) (Math.max(x[xI + 3], y[yI + 3])); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y[yI + 0])); } return target; }
	public static double[] maxD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff) { return maxD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] maxD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff) { return maxD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] maxD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff) { return maxD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] maxD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff) { return maxD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] maxD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff) { return maxD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] maxD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff) { return maxD(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static double[] maxD(int[] x, int[] y, double[] target) { return maxD(x, 0, y, 0, target, 0); }
	public static double[] maxD(long[] x, long[] y, double[] target) { return maxD(x, 0, y, 0, target, 0); }
	public static double[] maxD(short[] x, short[] y, double[] target) { return maxD(x, 0, y, 0, target, 0); }
	public static double[] maxD(float[] x, float[] y, double[] target) { return maxD(x, 0, y, 0, target, 0); }
	public static double[] maxD(double[] x, double[] y, double[] target) { return maxD(x, 0, y, 0, target, 0); }
	public static double[] maxD(char[] x, char[] y, double[] target) { return maxD(x, 0, y, 0, target, 0); }
	public static double[] maxD(int[] x, int[] y) { return maxD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] maxD(long[] x, long[] y) { return maxD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] maxD(short[] x, short[] y) { return maxD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] maxD(float[] x, float[] y) { return maxD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] maxD(double[] x, double[] y) { return maxD(x, y, new double[(x.length + y.length) / 2]); }
	public static double[] maxD(char[] x, char[] y) { return maxD(x, y, new double[(x.length + y.length) / 2]); }

	public static double[] maxD(int[] x, int xOff, int y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); target[targetI + 1] = (double) (Math.max(x[xI + 1], y)); target[targetI + 2] = (double) (Math.max(x[xI + 2], y)); target[targetI + 3] = (double) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); } return target; }
	public static double[] maxD(long[] x, int xOff, long y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); target[targetI + 1] = (double) (Math.max(x[xI + 1], y)); target[targetI + 2] = (double) (Math.max(x[xI + 2], y)); target[targetI + 3] = (double) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); } return target; }
	public static double[] maxD(short[] x, int xOff, short y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); target[targetI + 1] = (double) (Math.max(x[xI + 1], y)); target[targetI + 2] = (double) (Math.max(x[xI + 2], y)); target[targetI + 3] = (double) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); } return target; }
	public static double[] maxD(float[] x, int xOff, float y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); target[targetI + 1] = (double) (Math.max(x[xI + 1], y)); target[targetI + 2] = (double) (Math.max(x[xI + 2], y)); target[targetI + 3] = (double) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); } return target; }
	public static double[] maxD(double[] x, int xOff, double y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); target[targetI + 1] = (double) (Math.max(x[xI + 1], y)); target[targetI + 2] = (double) (Math.max(x[xI + 2], y)); target[targetI + 3] = (double) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); } return target; }
	public static double[] maxD(char[] x, int xOff, char y, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); target[targetI + 1] = (double) (Math.max(x[xI + 1], y)); target[targetI + 2] = (double) (Math.max(x[xI + 2], y)); target[targetI + 3] = (double) (Math.max(x[xI + 3], y)); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (Math.max(x[xI + 0], y)); } return target; }
	public static double[] maxD(int[] x, int xOff, int y, double[] target, int targetOff) { return maxD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] maxD(long[] x, int xOff, long y, double[] target, int targetOff) { return maxD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] maxD(short[] x, int xOff, short y, double[] target, int targetOff) { return maxD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] maxD(float[] x, int xOff, float y, double[] target, int targetOff) { return maxD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] maxD(double[] x, int xOff, double y, double[] target, int targetOff) { return maxD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] maxD(char[] x, int xOff, char y, double[] target, int targetOff) { return maxD(x, xOff, y, target, targetOff, target.length - targetOff); }
	public static double[] maxD(int[] x, int y, double[] target) { return maxD(x, 0, y, target, 0); }
	public static double[] maxD(long[] x, long y, double[] target) { return maxD(x, 0, y, target, 0); }
	public static double[] maxD(short[] x, short y, double[] target) { return maxD(x, 0, y, target, 0); }
	public static double[] maxD(float[] x, float y, double[] target) { return maxD(x, 0, y, target, 0); }
	public static double[] maxD(double[] x, double y, double[] target) { return maxD(x, 0, y, target, 0); }
	public static double[] maxD(char[] x, char y, double[] target) { return maxD(x, 0, y, target, 0); }
	public static double[] maxD(int[] x, int y) { return maxD(x, y, new double[(x.length) / 1]); }
	public static double[] maxD(long[] x, long y) { return maxD(x, y, new double[(x.length) / 1]); }
	public static double[] maxD(short[] x, short y) { return maxD(x, y, new double[(x.length) / 1]); }
	public static double[] maxD(float[] x, float y) { return maxD(x, y, new double[(x.length) / 1]); }
	public static double[] maxD(double[] x, double y) { return maxD(x, y, new double[(x.length) / 1]); }
	public static double[] maxD(char[] x, char y) { return maxD(x, y, new double[(x.length) / 1]); }

	// clamp
	public static int clamp(int x, int minVal, int maxVal) { return min(max(x, minVal), maxVal); }
	public static long clamp(long x, long minVal, long maxVal) { return min(max(x, minVal), maxVal); }
	public static short clamp(short x, short minVal, short maxVal) { return min(max(x, minVal), maxVal); }
	public static float clamp(float x, float minVal, float maxVal) { return min(max(x, minVal), maxVal); }
	public static double clamp(double x, double minVal, double maxVal) { return min(max(x, minVal), maxVal); }
	public static char clamp(char x, char minVal, char maxVal) { return min(max(x, minVal), maxVal); }
	public static int[] clamp(int[] x, int xOff, int[] minVal, int minValOff, int[] maxVal, int maxValOff, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (int) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (int) clamp(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (int) clamp(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (int) clamp(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (int) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static long[] clamp(long[] x, int xOff, long[] minVal, int minValOff, long[] maxVal, int maxValOff, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (long) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (long) clamp(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (long) clamp(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (long) clamp(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (long) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static short[] clamp(short[] x, int xOff, short[] minVal, int minValOff, short[] maxVal, int maxValOff, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (short) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (short) clamp(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (short) clamp(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (short) clamp(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (short) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static float[] clamp(float[] x, int xOff, float[] minVal, int minValOff, float[] maxVal, int maxValOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (float) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (float) clamp(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (float) clamp(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (float) clamp(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (float) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static double[] clamp(double[] x, int xOff, double[] minVal, int minValOff, double[] maxVal, int maxValOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (double) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (double) clamp(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (double) clamp(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (double) clamp(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (double) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static char[] clamp(char[] x, int xOff, char[] minVal, int minValOff, char[] maxVal, int maxValOff, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (char) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (char) clamp(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (char) clamp(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (char) clamp(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (char) clamp(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static int[] clamp(int[] x, int xOff, int[] minVal, int minValOff, int[] maxVal, int maxValOff, int[] target, int targetOff) { return clamp(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static long[] clamp(long[] x, int xOff, long[] minVal, int minValOff, long[] maxVal, int maxValOff, long[] target, int targetOff) { return clamp(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static short[] clamp(short[] x, int xOff, short[] minVal, int minValOff, short[] maxVal, int maxValOff, short[] target, int targetOff) { return clamp(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static float[] clamp(float[] x, int xOff, float[] minVal, int minValOff, float[] maxVal, int maxValOff, float[] target, int targetOff) { return clamp(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static double[] clamp(double[] x, int xOff, double[] minVal, int minValOff, double[] maxVal, int maxValOff, double[] target, int targetOff) { return clamp(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static char[] clamp(char[] x, int xOff, char[] minVal, int minValOff, char[] maxVal, int maxValOff, char[] target, int targetOff) { return clamp(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static int[] clamp(int[] x, int[] minVal, int[] maxVal, int[] target) { return clamp(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static long[] clamp(long[] x, long[] minVal, long[] maxVal, long[] target) { return clamp(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static short[] clamp(short[] x, short[] minVal, short[] maxVal, short[] target) { return clamp(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static float[] clamp(float[] x, float[] minVal, float[] maxVal, float[] target) { return clamp(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static double[] clamp(double[] x, double[] minVal, double[] maxVal, double[] target) { return clamp(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static char[] clamp(char[] x, char[] minVal, char[] maxVal, char[] target) { return clamp(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static int[] clamp(int[] x, int[] minVal, int[] maxVal) { return clamp(x, minVal, maxVal, new int[(x.length + minVal.length + maxVal.length) / 3]); }
	public static long[] clamp(long[] x, long[] minVal, long[] maxVal) { return clamp(x, minVal, maxVal, new long[(x.length + minVal.length + maxVal.length) / 3]); }
	public static short[] clamp(short[] x, short[] minVal, short[] maxVal) { return clamp(x, minVal, maxVal, new short[(x.length + minVal.length + maxVal.length) / 3]); }
	public static float[] clamp(float[] x, float[] minVal, float[] maxVal) { return clamp(x, minVal, maxVal, new float[(x.length + minVal.length + maxVal.length) / 3]); }
	public static double[] clamp(double[] x, double[] minVal, double[] maxVal) { return clamp(x, minVal, maxVal, new double[(x.length + minVal.length + maxVal.length) / 3]); }
	public static char[] clamp(char[] x, char[] minVal, char[] maxVal) { return clamp(x, minVal, maxVal, new char[(x.length + minVal.length + maxVal.length) / 3]); }

	public static int[] clamp(int[] x, int xOff, int minVal, int maxVal, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (int) clamp(x[xI + 0], minVal, maxVal); target[targetI + 1] = (int) clamp(x[xI + 1], minVal, maxVal); target[targetI + 2] = (int) clamp(x[xI + 2], minVal, maxVal); target[targetI + 3] = (int) clamp(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (int) clamp(x[xI + 0], minVal, maxVal); } return target; }
	public static long[] clamp(long[] x, int xOff, long minVal, long maxVal, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (long) clamp(x[xI + 0], minVal, maxVal); target[targetI + 1] = (long) clamp(x[xI + 1], minVal, maxVal); target[targetI + 2] = (long) clamp(x[xI + 2], minVal, maxVal); target[targetI + 3] = (long) clamp(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (long) clamp(x[xI + 0], minVal, maxVal); } return target; }
	public static short[] clamp(short[] x, int xOff, short minVal, short maxVal, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (short) clamp(x[xI + 0], minVal, maxVal); target[targetI + 1] = (short) clamp(x[xI + 1], minVal, maxVal); target[targetI + 2] = (short) clamp(x[xI + 2], minVal, maxVal); target[targetI + 3] = (short) clamp(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (short) clamp(x[xI + 0], minVal, maxVal); } return target; }
	public static float[] clamp(float[] x, int xOff, float minVal, float maxVal, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (float) clamp(x[xI + 0], minVal, maxVal); target[targetI + 1] = (float) clamp(x[xI + 1], minVal, maxVal); target[targetI + 2] = (float) clamp(x[xI + 2], minVal, maxVal); target[targetI + 3] = (float) clamp(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (float) clamp(x[xI + 0], minVal, maxVal); } return target; }
	public static double[] clamp(double[] x, int xOff, double minVal, double maxVal, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) clamp(x[xI + 0], minVal, maxVal); target[targetI + 1] = (double) clamp(x[xI + 1], minVal, maxVal); target[targetI + 2] = (double) clamp(x[xI + 2], minVal, maxVal); target[targetI + 3] = (double) clamp(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) clamp(x[xI + 0], minVal, maxVal); } return target; }
	public static char[] clamp(char[] x, int xOff, char minVal, char maxVal, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (char) clamp(x[xI + 0], minVal, maxVal); target[targetI + 1] = (char) clamp(x[xI + 1], minVal, maxVal); target[targetI + 2] = (char) clamp(x[xI + 2], minVal, maxVal); target[targetI + 3] = (char) clamp(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (char) clamp(x[xI + 0], minVal, maxVal); } return target; }
	public static int[] clamp(int[] x, int xOff, int minVal, int maxVal, int[] target, int targetOff) { return clamp(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static long[] clamp(long[] x, int xOff, long minVal, long maxVal, long[] target, int targetOff) { return clamp(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static short[] clamp(short[] x, int xOff, short minVal, short maxVal, short[] target, int targetOff) { return clamp(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static float[] clamp(float[] x, int xOff, float minVal, float maxVal, float[] target, int targetOff) { return clamp(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static double[] clamp(double[] x, int xOff, double minVal, double maxVal, double[] target, int targetOff) { return clamp(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static char[] clamp(char[] x, int xOff, char minVal, char maxVal, char[] target, int targetOff) { return clamp(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static int[] clamp(int[] x, int minVal, int maxVal, int[] target) { return clamp(x, 0, minVal, maxVal, target, 0); }
	public static long[] clamp(long[] x, long minVal, long maxVal, long[] target) { return clamp(x, 0, minVal, maxVal, target, 0); }
	public static short[] clamp(short[] x, short minVal, short maxVal, short[] target) { return clamp(x, 0, minVal, maxVal, target, 0); }
	public static float[] clamp(float[] x, float minVal, float maxVal, float[] target) { return clamp(x, 0, minVal, maxVal, target, 0); }
	public static double[] clamp(double[] x, double minVal, double maxVal, double[] target) { return clamp(x, 0, minVal, maxVal, target, 0); }
	public static char[] clamp(char[] x, char minVal, char maxVal, char[] target) { return clamp(x, 0, minVal, maxVal, target, 0); }
	public static int[] clamp(int[] x, int minVal, int maxVal) { return clamp(x, minVal, maxVal, new int[(x.length) / 1]); }
	public static long[] clamp(long[] x, long minVal, long maxVal) { return clamp(x, minVal, maxVal, new long[(x.length) / 1]); }
	public static short[] clamp(short[] x, short minVal, short maxVal) { return clamp(x, minVal, maxVal, new short[(x.length) / 1]); }
	public static float[] clamp(float[] x, float minVal, float maxVal) { return clamp(x, minVal, maxVal, new float[(x.length) / 1]); }
	public static double[] clamp(double[] x, double minVal, double maxVal) { return clamp(x, minVal, maxVal, new double[(x.length) / 1]); }
	public static char[] clamp(char[] x, char minVal, char maxVal) { return clamp(x, minVal, maxVal, new char[(x.length) / 1]); }

	protected static double clampD(int x, int minVal, int maxVal) { return (double) clamp(x, minVal, maxVal); }
	protected static double clampD(long x, long minVal, long maxVal) { return (double) clamp(x, minVal, maxVal); }
	protected static double clampD(short x, short minVal, short maxVal) { return (double) clamp(x, minVal, maxVal); }
	protected static double clampD(float x, float minVal, float maxVal) { return (double) clamp(x, minVal, maxVal); }
	protected static double clampD(double x, double minVal, double maxVal) { return (double) clamp(x, minVal, maxVal); }
	protected static double clampD(char x, char minVal, char maxVal) { return (double) clamp(x, minVal, maxVal); }
	public static double[] clampD(int[] x, int xOff, int[] minVal, int minValOff, int[] maxVal, int maxValOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (double) clampD(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (double) clampD(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (double) clampD(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static double[] clampD(long[] x, int xOff, long[] minVal, int minValOff, long[] maxVal, int maxValOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (double) clampD(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (double) clampD(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (double) clampD(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static double[] clampD(short[] x, int xOff, short[] minVal, int minValOff, short[] maxVal, int maxValOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (double) clampD(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (double) clampD(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (double) clampD(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static double[] clampD(float[] x, int xOff, float[] minVal, int minValOff, float[] maxVal, int maxValOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (double) clampD(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (double) clampD(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (double) clampD(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static double[] clampD(double[] x, int xOff, double[] minVal, int minValOff, double[] maxVal, int maxValOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (double) clampD(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (double) clampD(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (double) clampD(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static double[] clampD(char[] x, int xOff, char[] minVal, int minValOff, char[] maxVal, int maxValOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(minValOff, minVal.length, len); ArrayUtils.assertIndex(maxValOff, maxVal.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int minValI = minValOff; int maxValI = maxValOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, minValI += 4, maxValI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); target[targetI + 1] = (double) clampD(x[xI + 1], minVal[minValI + 1], maxVal[maxValI + 1]); target[targetI + 2] = (double) clampD(x[xI + 2], minVal[minValI + 2], maxVal[maxValI + 2]); target[targetI + 3] = (double) clampD(x[xI + 3], minVal[minValI + 3], maxVal[maxValI + 3]); } for(; i < len; i++, xI++, minValI++, maxValI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal[minValI + 0], maxVal[maxValI + 0]); } return target; }
	public static double[] clampD(int[] x, int xOff, int[] minVal, int minValOff, int[] maxVal, int maxValOff, double[] target, int targetOff) { return clampD(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static double[] clampD(long[] x, int xOff, long[] minVal, int minValOff, long[] maxVal, int maxValOff, double[] target, int targetOff) { return clampD(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static double[] clampD(short[] x, int xOff, short[] minVal, int minValOff, short[] maxVal, int maxValOff, double[] target, int targetOff) { return clampD(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static double[] clampD(float[] x, int xOff, float[] minVal, int minValOff, float[] maxVal, int maxValOff, double[] target, int targetOff) { return clampD(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static double[] clampD(double[] x, int xOff, double[] minVal, int minValOff, double[] maxVal, int maxValOff, double[] target, int targetOff) { return clampD(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static double[] clampD(char[] x, int xOff, char[] minVal, int minValOff, char[] maxVal, int maxValOff, double[] target, int targetOff) { return clampD(x, xOff, minVal, minValOff, maxVal, maxValOff, target, targetOff, target.length - targetOff); }
	public static double[] clampD(int[] x, int[] minVal, int[] maxVal, double[] target) { return clampD(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static double[] clampD(long[] x, long[] minVal, long[] maxVal, double[] target) { return clampD(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static double[] clampD(short[] x, short[] minVal, short[] maxVal, double[] target) { return clampD(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static double[] clampD(float[] x, float[] minVal, float[] maxVal, double[] target) { return clampD(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static double[] clampD(double[] x, double[] minVal, double[] maxVal, double[] target) { return clampD(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static double[] clampD(char[] x, char[] minVal, char[] maxVal, double[] target) { return clampD(x, 0, minVal, 0, maxVal, 0, target, 0); }
	public static double[] clampD(int[] x, int[] minVal, int[] maxVal) { return clampD(x, minVal, maxVal, new double[(x.length + minVal.length + maxVal.length) / 3]); }
	public static double[] clampD(long[] x, long[] minVal, long[] maxVal) { return clampD(x, minVal, maxVal, new double[(x.length + minVal.length + maxVal.length) / 3]); }
	public static double[] clampD(short[] x, short[] minVal, short[] maxVal) { return clampD(x, minVal, maxVal, new double[(x.length + minVal.length + maxVal.length) / 3]); }
	public static double[] clampD(float[] x, float[] minVal, float[] maxVal) { return clampD(x, minVal, maxVal, new double[(x.length + minVal.length + maxVal.length) / 3]); }
	public static double[] clampD(double[] x, double[] minVal, double[] maxVal) { return clampD(x, minVal, maxVal, new double[(x.length + minVal.length + maxVal.length) / 3]); }
	public static double[] clampD(char[] x, char[] minVal, char[] maxVal) { return clampD(x, minVal, maxVal, new double[(x.length + minVal.length + maxVal.length) / 3]); }

	public static double[] clampD(int[] x, int xOff, int minVal, int maxVal, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); target[targetI + 1] = (double) clampD(x[xI + 1], minVal, maxVal); target[targetI + 2] = (double) clampD(x[xI + 2], minVal, maxVal); target[targetI + 3] = (double) clampD(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); } return target; }
	public static double[] clampD(long[] x, int xOff, long minVal, long maxVal, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); target[targetI + 1] = (double) clampD(x[xI + 1], minVal, maxVal); target[targetI + 2] = (double) clampD(x[xI + 2], minVal, maxVal); target[targetI + 3] = (double) clampD(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); } return target; }
	public static double[] clampD(short[] x, int xOff, short minVal, short maxVal, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); target[targetI + 1] = (double) clampD(x[xI + 1], minVal, maxVal); target[targetI + 2] = (double) clampD(x[xI + 2], minVal, maxVal); target[targetI + 3] = (double) clampD(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); } return target; }
	public static double[] clampD(float[] x, int xOff, float minVal, float maxVal, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); target[targetI + 1] = (double) clampD(x[xI + 1], minVal, maxVal); target[targetI + 2] = (double) clampD(x[xI + 2], minVal, maxVal); target[targetI + 3] = (double) clampD(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); } return target; }
	public static double[] clampD(double[] x, int xOff, double minVal, double maxVal, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); target[targetI + 1] = (double) clampD(x[xI + 1], minVal, maxVal); target[targetI + 2] = (double) clampD(x[xI + 2], minVal, maxVal); target[targetI + 3] = (double) clampD(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); } return target; }
	public static double[] clampD(char[] x, int xOff, char minVal, char maxVal, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); target[targetI + 1] = (double) clampD(x[xI + 1], minVal, maxVal); target[targetI + 2] = (double) clampD(x[xI + 2], minVal, maxVal); target[targetI + 3] = (double) clampD(x[xI + 3], minVal, maxVal); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) clampD(x[xI + 0], minVal, maxVal); } return target; }
	public static double[] clampD(int[] x, int xOff, int minVal, int maxVal, double[] target, int targetOff) { return clampD(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static double[] clampD(long[] x, int xOff, long minVal, long maxVal, double[] target, int targetOff) { return clampD(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static double[] clampD(short[] x, int xOff, short minVal, short maxVal, double[] target, int targetOff) { return clampD(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static double[] clampD(float[] x, int xOff, float minVal, float maxVal, double[] target, int targetOff) { return clampD(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static double[] clampD(double[] x, int xOff, double minVal, double maxVal, double[] target, int targetOff) { return clampD(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static double[] clampD(char[] x, int xOff, char minVal, char maxVal, double[] target, int targetOff) { return clampD(x, xOff, minVal, maxVal, target, targetOff, target.length - targetOff); }
	public static double[] clampD(int[] x, int minVal, int maxVal, double[] target) { return clampD(x, 0, minVal, maxVal, target, 0); }
	public static double[] clampD(long[] x, long minVal, long maxVal, double[] target) { return clampD(x, 0, minVal, maxVal, target, 0); }
	public static double[] clampD(short[] x, short minVal, short maxVal, double[] target) { return clampD(x, 0, minVal, maxVal, target, 0); }
	public static double[] clampD(float[] x, float minVal, float maxVal, double[] target) { return clampD(x, 0, minVal, maxVal, target, 0); }
	public static double[] clampD(double[] x, double minVal, double maxVal, double[] target) { return clampD(x, 0, minVal, maxVal, target, 0); }
	public static double[] clampD(char[] x, char minVal, char maxVal, double[] target) { return clampD(x, 0, minVal, maxVal, target, 0); }
	public static double[] clampD(int[] x, int minVal, int maxVal) { return clampD(x, minVal, maxVal, new double[(x.length) / 1]); }
	public static double[] clampD(long[] x, long minVal, long maxVal) { return clampD(x, minVal, maxVal, new double[(x.length) / 1]); }
	public static double[] clampD(short[] x, short minVal, short maxVal) { return clampD(x, minVal, maxVal, new double[(x.length) / 1]); }
	public static double[] clampD(float[] x, float minVal, float maxVal) { return clampD(x, minVal, maxVal, new double[(x.length) / 1]); }
	public static double[] clampD(double[] x, double minVal, double maxVal) { return clampD(x, minVal, maxVal, new double[(x.length) / 1]); }
	public static double[] clampD(char[] x, char minVal, char maxVal) { return clampD(x, minVal, maxVal, new double[(x.length) / 1]); }

	// mix
	public static double mix(double x, double y, double a) { return x * (1 - a) + y * a; } // NumberUtils.map(a, 0, 1, x, y);
	public static double[] mix(int[] x, int xOff, int[] y, int yOff, int[] a, int aOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(aOff, a.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int aI = aOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, aI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a[aI + 1]); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a[aI + 2]); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a[aI + 3]); } for(; i < len; i++, xI++, yI++, aI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); } return target; }
	public static double[] mix(long[] x, int xOff, long[] y, int yOff, long[] a, int aOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(aOff, a.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int aI = aOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, aI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a[aI + 1]); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a[aI + 2]); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a[aI + 3]); } for(; i < len; i++, xI++, yI++, aI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); } return target; }
	public static double[] mix(short[] x, int xOff, short[] y, int yOff, short[] a, int aOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(aOff, a.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int aI = aOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, aI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a[aI + 1]); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a[aI + 2]); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a[aI + 3]); } for(; i < len; i++, xI++, yI++, aI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); } return target; }
	public static double[] mix(float[] x, int xOff, float[] y, int yOff, float[] a, int aOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(aOff, a.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int aI = aOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, aI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a[aI + 1]); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a[aI + 2]); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a[aI + 3]); } for(; i < len; i++, xI++, yI++, aI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); } return target; }
	public static double[] mix(double[] x, int xOff, double[] y, int yOff, double[] a, int aOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(aOff, a.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int aI = aOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, aI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a[aI + 1]); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a[aI + 2]); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a[aI + 3]); } for(; i < len; i++, xI++, yI++, aI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); } return target; }
	public static double[] mix(char[] x, int xOff, char[] y, int yOff, char[] a, int aOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(aOff, a.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int aI = aOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, aI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a[aI + 1]); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a[aI + 2]); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a[aI + 3]); } for(; i < len; i++, xI++, yI++, aI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a[aI + 0]); } return target; }
	public static double[] mix(int[] x, int xOff, int[] y, int yOff, int[] a, int aOff, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, aOff, target, targetOff, target.length - targetOff); }
	public static double[] mix(long[] x, int xOff, long[] y, int yOff, long[] a, int aOff, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, aOff, target, targetOff, target.length - targetOff); }
	public static double[] mix(short[] x, int xOff, short[] y, int yOff, short[] a, int aOff, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, aOff, target, targetOff, target.length - targetOff); }
	public static double[] mix(float[] x, int xOff, float[] y, int yOff, float[] a, int aOff, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, aOff, target, targetOff, target.length - targetOff); }
	public static double[] mix(double[] x, int xOff, double[] y, int yOff, double[] a, int aOff, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, aOff, target, targetOff, target.length - targetOff); }
	public static double[] mix(char[] x, int xOff, char[] y, int yOff, char[] a, int aOff, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, aOff, target, targetOff, target.length - targetOff); }
	public static double[] mix(int[] x, int[] y, int[] a, double[] target) { return mix(x, 0, y, 0, a, 0, target, 0); }
	public static double[] mix(long[] x, long[] y, long[] a, double[] target) { return mix(x, 0, y, 0, a, 0, target, 0); }
	public static double[] mix(short[] x, short[] y, short[] a, double[] target) { return mix(x, 0, y, 0, a, 0, target, 0); }
	public static double[] mix(float[] x, float[] y, float[] a, double[] target) { return mix(x, 0, y, 0, a, 0, target, 0); }
	public static double[] mix(double[] x, double[] y, double[] a, double[] target) { return mix(x, 0, y, 0, a, 0, target, 0); }
	public static double[] mix(char[] x, char[] y, char[] a, double[] target) { return mix(x, 0, y, 0, a, 0, target, 0); }
	public static double[] mix(int[] x, int[] y, int[] a) { return mix(x, y, a, new double[(x.length + y.length + a.length) / 3]); }
	public static double[] mix(long[] x, long[] y, long[] a) { return mix(x, y, a, new double[(x.length + y.length + a.length) / 3]); }
	public static double[] mix(short[] x, short[] y, short[] a) { return mix(x, y, a, new double[(x.length + y.length + a.length) / 3]); }
	public static double[] mix(float[] x, float[] y, float[] a) { return mix(x, y, a, new double[(x.length + y.length + a.length) / 3]); }
	public static double[] mix(double[] x, double[] y, double[] a) { return mix(x, y, a, new double[(x.length + y.length + a.length) / 3]); }
	public static double[] mix(char[] x, char[] y, char[] a) { return mix(x, y, a, new double[(x.length + y.length + a.length) / 3]); }

	public static double[] mix(int[] x, int xOff, int[] y, int yOff, int a, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); } return target; }
	public static double[] mix(long[] x, int xOff, long[] y, int yOff, long a, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); } return target; }
	public static double[] mix(short[] x, int xOff, short[] y, int yOff, short a, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); } return target; }
	public static double[] mix(float[] x, int xOff, float[] y, int yOff, float a, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); } return target; }
	public static double[] mix(double[] x, int xOff, double[] y, int yOff, double a, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); } return target; }
	public static double[] mix(char[] x, int xOff, char[] y, int yOff, char a, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); target[targetI + 1] = (double) mix(x[xI + 1], y[yI + 1], a); target[targetI + 2] = (double) mix(x[xI + 2], y[yI + 2], a); target[targetI + 3] = (double) mix(x[xI + 3], y[yI + 3], a); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (double) mix(x[xI + 0], y[yI + 0], a); } return target; }
	public static double[] mix(int[] x, int xOff, int[] y, int yOff, int a, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, target, targetOff, target.length - targetOff); }
	public static double[] mix(long[] x, int xOff, long[] y, int yOff, long a, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, target, targetOff, target.length - targetOff); }
	public static double[] mix(short[] x, int xOff, short[] y, int yOff, short a, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, target, targetOff, target.length - targetOff); }
	public static double[] mix(float[] x, int xOff, float[] y, int yOff, float a, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, target, targetOff, target.length - targetOff); }
	public static double[] mix(double[] x, int xOff, double[] y, int yOff, double a, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, target, targetOff, target.length - targetOff); }
	public static double[] mix(char[] x, int xOff, char[] y, int yOff, char a, double[] target, int targetOff) { return mix(x, xOff, y, yOff, a, target, targetOff, target.length - targetOff); }
	public static double[] mix(int[] x, int[] y, int a, double[] target) { return mix(x, 0, y, 0, a, target, 0); }
	public static double[] mix(long[] x, long[] y, long a, double[] target) { return mix(x, 0, y, 0, a, target, 0); }
	public static double[] mix(short[] x, short[] y, short a, double[] target) { return mix(x, 0, y, 0, a, target, 0); }
	public static double[] mix(float[] x, float[] y, float a, double[] target) { return mix(x, 0, y, 0, a, target, 0); }
	public static double[] mix(double[] x, double[] y, double a, double[] target) { return mix(x, 0, y, 0, a, target, 0); }
	public static double[] mix(char[] x, char[] y, char a, double[] target) { return mix(x, 0, y, 0, a, target, 0); }
	public static double[] mix(int[] x, int[] y, int a) { return mix(x, y, a, new double[(x.length + y.length) / 2]); }
	public static double[] mix(long[] x, long[] y, long a) { return mix(x, y, a, new double[(x.length + y.length) / 2]); }
	public static double[] mix(short[] x, short[] y, short a) { return mix(x, y, a, new double[(x.length + y.length) / 2]); }
	public static double[] mix(float[] x, float[] y, float a) { return mix(x, y, a, new double[(x.length + y.length) / 2]); }
	public static double[] mix(double[] x, double[] y, double a) { return mix(x, y, a, new double[(x.length + y.length) / 2]); }
	public static double[] mix(char[] x, char[] y, char a) { return mix(x, y, a, new double[(x.length + y.length) / 2]); }

	// step
	public static double step(double x, double edge) { return x > edge ? 1.0 : 0.0; }
	public static double[] step(int[] x, int xOff, int[] edge, int edgeOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(edgeOff, edge.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int edgeI = edgeOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, edgeI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge[edgeI + 1] ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge[edgeI + 2] ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge[edgeI + 3] ? 1.0 : 0.0); } for(; i < len; i++, xI++, edgeI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); } return target; }
	public static double[] step(long[] x, int xOff, long[] edge, int edgeOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(edgeOff, edge.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int edgeI = edgeOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, edgeI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge[edgeI + 1] ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge[edgeI + 2] ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge[edgeI + 3] ? 1.0 : 0.0); } for(; i < len; i++, xI++, edgeI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); } return target; }
	public static double[] step(short[] x, int xOff, short[] edge, int edgeOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(edgeOff, edge.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int edgeI = edgeOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, edgeI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge[edgeI + 1] ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge[edgeI + 2] ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge[edgeI + 3] ? 1.0 : 0.0); } for(; i < len; i++, xI++, edgeI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); } return target; }
	public static double[] step(float[] x, int xOff, float[] edge, int edgeOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(edgeOff, edge.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int edgeI = edgeOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, edgeI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge[edgeI + 1] ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge[edgeI + 2] ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge[edgeI + 3] ? 1.0 : 0.0); } for(; i < len; i++, xI++, edgeI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); } return target; }
	public static double[] step(double[] x, int xOff, double[] edge, int edgeOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(edgeOff, edge.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int edgeI = edgeOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, edgeI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge[edgeI + 1] ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge[edgeI + 2] ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge[edgeI + 3] ? 1.0 : 0.0); } for(; i < len; i++, xI++, edgeI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); } return target; }
	public static double[] step(char[] x, int xOff, char[] edge, int edgeOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(edgeOff, edge.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int edgeI = edgeOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, edgeI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge[edgeI + 1] ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge[edgeI + 2] ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge[edgeI + 3] ? 1.0 : 0.0); } for(; i < len; i++, xI++, edgeI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge[edgeI + 0] ? 1.0 : 0.0); } return target; }
	public static double[] step(int[] x, int xOff, int[] edge, int edgeOff, double[] target, int targetOff) { return step(x, xOff, edge, edgeOff, target, targetOff, target.length - targetOff); }
	public static double[] step(long[] x, int xOff, long[] edge, int edgeOff, double[] target, int targetOff) { return step(x, xOff, edge, edgeOff, target, targetOff, target.length - targetOff); }
	public static double[] step(short[] x, int xOff, short[] edge, int edgeOff, double[] target, int targetOff) { return step(x, xOff, edge, edgeOff, target, targetOff, target.length - targetOff); }
	public static double[] step(float[] x, int xOff, float[] edge, int edgeOff, double[] target, int targetOff) { return step(x, xOff, edge, edgeOff, target, targetOff, target.length - targetOff); }
	public static double[] step(double[] x, int xOff, double[] edge, int edgeOff, double[] target, int targetOff) { return step(x, xOff, edge, edgeOff, target, targetOff, target.length - targetOff); }
	public static double[] step(char[] x, int xOff, char[] edge, int edgeOff, double[] target, int targetOff) { return step(x, xOff, edge, edgeOff, target, targetOff, target.length - targetOff); }
	public static double[] step(int[] x, int[] edge, double[] target) { return step(x, 0, edge, 0, target, 0); }
	public static double[] step(long[] x, long[] edge, double[] target) { return step(x, 0, edge, 0, target, 0); }
	public static double[] step(short[] x, short[] edge, double[] target) { return step(x, 0, edge, 0, target, 0); }
	public static double[] step(float[] x, float[] edge, double[] target) { return step(x, 0, edge, 0, target, 0); }
	public static double[] step(double[] x, double[] edge, double[] target) { return step(x, 0, edge, 0, target, 0); }
	public static double[] step(char[] x, char[] edge, double[] target) { return step(x, 0, edge, 0, target, 0); }
	public static double[] step(int[] x, int[] edge) { return step(x, edge, new double[(x.length + edge.length) / 2]); }
	public static double[] step(long[] x, long[] edge) { return step(x, edge, new double[(x.length + edge.length) / 2]); }
	public static double[] step(short[] x, short[] edge) { return step(x, edge, new double[(x.length + edge.length) / 2]); }
	public static double[] step(float[] x, float[] edge) { return step(x, edge, new double[(x.length + edge.length) / 2]); }
	public static double[] step(double[] x, double[] edge) { return step(x, edge, new double[(x.length + edge.length) / 2]); }
	public static double[] step(char[] x, char[] edge) { return step(x, edge, new double[(x.length + edge.length) / 2]); }

	public static double[] step(int[] x, int xOff, int edge, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge ? 1.0 : 0.0); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); } return target; }
	public static double[] step(long[] x, int xOff, long edge, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge ? 1.0 : 0.0); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); } return target; }
	public static double[] step(short[] x, int xOff, short edge, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge ? 1.0 : 0.0); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); } return target; }
	public static double[] step(float[] x, int xOff, float edge, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge ? 1.0 : 0.0); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); } return target; }
	public static double[] step(double[] x, int xOff, double edge, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge ? 1.0 : 0.0); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); } return target; }
	public static double[] step(char[] x, int xOff, char edge, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); target[targetI + 1] = (double) (x[xI + 1] > edge ? 1.0 : 0.0); target[targetI + 2] = (double) (x[xI + 2] > edge ? 1.0 : 0.0); target[targetI + 3] = (double) (x[xI + 3] > edge ? 1.0 : 0.0); } for(; i < len; i++, xI++, targetI++) target[targetI + 0] = (double) (x[xI + 0] > edge ? 1.0 : 0.0); } return target; }
	public static double[] step(int[] x, int xOff, int edge, double[] target, int targetOff) { return step(x, xOff, edge, target, targetOff, target.length - targetOff); }
	public static double[] step(long[] x, int xOff, long edge, double[] target, int targetOff) { return step(x, xOff, edge, target, targetOff, target.length - targetOff); }
	public static double[] step(short[] x, int xOff, short edge, double[] target, int targetOff) { return step(x, xOff, edge, target, targetOff, target.length - targetOff); }
	public static double[] step(float[] x, int xOff, float edge, double[] target, int targetOff) { return step(x, xOff, edge, target, targetOff, target.length - targetOff); }
	public static double[] step(double[] x, int xOff, double edge, double[] target, int targetOff) { return step(x, xOff, edge, target, targetOff, target.length - targetOff); }
	public static double[] step(char[] x, int xOff, char edge, double[] target, int targetOff) { return step(x, xOff, edge, target, targetOff, target.length - targetOff); }
	public static double[] step(int[] x, int edge, double[] target) { return step(x, 0, edge, target, 0); }
	public static double[] step(long[] x, long edge, double[] target) { return step(x, 0, edge, target, 0); }
	public static double[] step(short[] x, short edge, double[] target) { return step(x, 0, edge, target, 0); }
	public static double[] step(float[] x, float edge, double[] target) { return step(x, 0, edge, target, 0); }
	public static double[] step(double[] x, double edge, double[] target) { return step(x, 0, edge, target, 0); }
	public static double[] step(char[] x, char edge, double[] target) { return step(x, 0, edge, target, 0); }
	public static double[] step(int[] x, int edge) { return step(x, edge, new double[(x.length) / 1]); }
	public static double[] step(long[] x, long edge) { return step(x, edge, new double[(x.length) / 1]); }
	public static double[] step(short[] x, short edge) { return step(x, edge, new double[(x.length) / 1]); }
	public static double[] step(float[] x, float edge) { return step(x, edge, new double[(x.length) / 1]); }
	public static double[] step(double[] x, double edge) { return step(x, edge, new double[(x.length) / 1]); }
	public static double[] step(char[] x, char edge) { return step(x, edge, new double[(x.length) / 1]); }

	// smooth step
	// https://en.wikipedia.org/wiki/Smoothstep#Variations
	public static double smoothstep(double edge0, double edge1, double x) {
		x = clamp((x - edge0) / (edge1 - edge0), 0.0, 1.0);
		return x * x * x * (x * (x * 6 - 15) + 10);
	}
	public static double[] smoothstep(int[] edge0, int edge0Off, int[] edge1, int edge1Off, int[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x[xI + 1]); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x[xI + 2]); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x[xI + 3]); } for(; i < len; i++, edge0I++, edge1I++, xI++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); } return target; }
	public static double[] smoothstep(long[] edge0, int edge0Off, long[] edge1, int edge1Off, long[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x[xI + 1]); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x[xI + 2]); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x[xI + 3]); } for(; i < len; i++, edge0I++, edge1I++, xI++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); } return target; }
	public static double[] smoothstep(short[] edge0, int edge0Off, short[] edge1, int edge1Off, short[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x[xI + 1]); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x[xI + 2]); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x[xI + 3]); } for(; i < len; i++, edge0I++, edge1I++, xI++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); } return target; }
	public static double[] smoothstep(float[] edge0, int edge0Off, float[] edge1, int edge1Off, float[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x[xI + 1]); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x[xI + 2]); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x[xI + 3]); } for(; i < len; i++, edge0I++, edge1I++, xI++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); } return target; }
	public static double[] smoothstep(double[] edge0, int edge0Off, double[] edge1, int edge1Off, double[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x[xI + 1]); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x[xI + 2]); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x[xI + 3]); } for(; i < len; i++, edge0I++, edge1I++, xI++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); } return target; }
	public static double[] smoothstep(char[] edge0, int edge0Off, char[] edge1, int edge1Off, char[] x, int xOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int xI = xOff; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, xI += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x[xI + 1]); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x[xI + 2]); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x[xI + 3]); } for(; i < len; i++, edge0I++, edge1I++, xI++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x[xI + 0]); } return target; }
	public static double[] smoothstep(int[] edge0, int edge0Off, int[] edge1, int edge1Off, int[] x, int xOff, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(long[] edge0, int edge0Off, long[] edge1, int edge1Off, long[] x, int xOff, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(short[] edge0, int edge0Off, short[] edge1, int edge1Off, short[] x, int xOff, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(float[] edge0, int edge0Off, float[] edge1, int edge1Off, float[] x, int xOff, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(double[] edge0, int edge0Off, double[] edge1, int edge1Off, double[] x, int xOff, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(char[] edge0, int edge0Off, char[] edge1, int edge1Off, char[] x, int xOff, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, xOff, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(int[] edge0, int[] edge1, int[] x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, 0, target, 0); }
	public static double[] smoothstep(long[] edge0, long[] edge1, long[] x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, 0, target, 0); }
	public static double[] smoothstep(short[] edge0, short[] edge1, short[] x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, 0, target, 0); }
	public static double[] smoothstep(float[] edge0, float[] edge1, float[] x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, 0, target, 0); }
	public static double[] smoothstep(double[] edge0, double[] edge1, double[] x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, 0, target, 0); }
	public static double[] smoothstep(char[] edge0, char[] edge1, char[] x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, 0, target, 0); }
	public static double[] smoothstep(int[] edge0, int[] edge1, int[] x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length + x.length) / 3]); }
	public static double[] smoothstep(long[] edge0, long[] edge1, long[] x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length + x.length) / 3]); }
	public static double[] smoothstep(short[] edge0, short[] edge1, short[] x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length + x.length) / 3]); }
	public static double[] smoothstep(float[] edge0, float[] edge1, float[] x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length + x.length) / 3]); }
	public static double[] smoothstep(double[] edge0, double[] edge1, double[] x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length + x.length) / 3]); }
	public static double[] smoothstep(char[] edge0, char[] edge1, char[] x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length + x.length) / 3]); }

	public static double[] smoothstep(int[] edge0, int edge0Off, int[] edge1, int edge1Off, int x, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x); } for(; i < len; i++, edge0I++, edge1I++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); } return target; }
	public static double[] smoothstep(long[] edge0, int edge0Off, long[] edge1, int edge1Off, long x, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x); } for(; i < len; i++, edge0I++, edge1I++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); } return target; }
	public static double[] smoothstep(short[] edge0, int edge0Off, short[] edge1, int edge1Off, short x, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x); } for(; i < len; i++, edge0I++, edge1I++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); } return target; }
	public static double[] smoothstep(float[] edge0, int edge0Off, float[] edge1, int edge1Off, float x, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x); } for(; i < len; i++, edge0I++, edge1I++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); } return target; }
	public static double[] smoothstep(double[] edge0, int edge0Off, double[] edge1, int edge1Off, double x, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x); } for(; i < len; i++, edge0I++, edge1I++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); } return target; }
	public static double[] smoothstep(char[] edge0, int edge0Off, char[] edge1, int edge1Off, char x, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(edge0Off, edge0.length, len); ArrayUtils.assertIndex(edge1Off, edge1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int edge0I = edge0Off; int edge1I = edge1Off; int targetI = targetOff; { for(; i < fit; i += 4, edge0I += 4, edge1I += 4, targetI += 4) { target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); target[targetI + 1] = (double) smoothstep(edge0[edge0Off + 1], edge1[edge1Off + 1], x); target[targetI + 2] = (double) smoothstep(edge0[edge0Off + 2], edge1[edge1Off + 2], x); target[targetI + 3] = (double) smoothstep(edge0[edge0Off + 3], edge1[edge1Off + 3], x); } for(; i < len; i++, edge0I++, edge1I++, targetI++) target[targetI + 0] = (double) smoothstep(edge0[edge0Off + 0], edge1[edge1Off + 0], x); } return target; }
	public static double[] smoothstep(int[] edge0, int edge0Off, int[] edge1, int edge1Off, int x, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(long[] edge0, int edge0Off, long[] edge1, int edge1Off, long x, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(short[] edge0, int edge0Off, short[] edge1, int edge1Off, short x, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(float[] edge0, int edge0Off, float[] edge1, int edge1Off, float x, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(double[] edge0, int edge0Off, double[] edge1, int edge1Off, double x, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(char[] edge0, int edge0Off, char[] edge1, int edge1Off, char x, double[] target, int targetOff) { return smoothstep(edge0, edge0Off, edge1, edge1Off, x, target, targetOff, target.length - targetOff); }
	public static double[] smoothstep(int[] edge0, int[] edge1, int x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, target, 0); }
	public static double[] smoothstep(long[] edge0, long[] edge1, long x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, target, 0); }
	public static double[] smoothstep(short[] edge0, short[] edge1, short x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, target, 0); }
	public static double[] smoothstep(float[] edge0, float[] edge1, float x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, target, 0); }
	public static double[] smoothstep(double[] edge0, double[] edge1, double x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, target, 0); }
	public static double[] smoothstep(char[] edge0, char[] edge1, char x, double[] target) { return smoothstep(edge0, 0, edge1, 0, x, target, 0); }
	public static double[] smoothstep(int[] edge0, int[] edge1, int x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length) / 2]); }
	public static double[] smoothstep(long[] edge0, long[] edge1, long x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length) / 2]); }
	public static double[] smoothstep(short[] edge0, short[] edge1, short x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length) / 2]); }
	public static double[] smoothstep(float[] edge0, float[] edge1, float x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length) / 2]); }
	public static double[] smoothstep(double[] edge0, double[] edge1, double x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length) / 2]); }
	public static double[] smoothstep(char[] edge0, char[] edge1, char x) { return smoothstep(edge0, edge1, x, new double[(edge0.length + edge1.length) / 2]); }

	// Easing
	public static double ease(double t, double d, double b, double c, Easing easing) { return easing.ease(t, b, c, d); }
	public static double[] ease(int t, int d, int[] b, int bOff, int[] c, int cOff, Easing easing, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(bOff, b.length, len); ArrayUtils.assertIndex(cOff, c.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int bI = bOff; int cI = cOff; int targetI = targetOff; { for(; i < fit; i += 4, bI += 4, cI += 4, targetI += 4) { target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); target[targetI + 1] = (double) (easing.ease(t, b[bI + 1], c[cI + 1], d)); target[targetI + 2] = (double) (easing.ease(t, b[bI + 2], c[cI + 2], d)); target[targetI + 3] = (double) (easing.ease(t, b[bI + 3], c[cI + 3], d)); } for(; i < len; i++, bI++, cI++, targetI++) target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); } return target; }
	public static double[] ease(long t, long d, long[] b, int bOff, long[] c, int cOff, Easing easing, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(bOff, b.length, len); ArrayUtils.assertIndex(cOff, c.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int bI = bOff; int cI = cOff; int targetI = targetOff; { for(; i < fit; i += 4, bI += 4, cI += 4, targetI += 4) { target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); target[targetI + 1] = (double) (easing.ease(t, b[bI + 1], c[cI + 1], d)); target[targetI + 2] = (double) (easing.ease(t, b[bI + 2], c[cI + 2], d)); target[targetI + 3] = (double) (easing.ease(t, b[bI + 3], c[cI + 3], d)); } for(; i < len; i++, bI++, cI++, targetI++) target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); } return target; }
	public static double[] ease(short t, short d, short[] b, int bOff, short[] c, int cOff, Easing easing, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(bOff, b.length, len); ArrayUtils.assertIndex(cOff, c.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int bI = bOff; int cI = cOff; int targetI = targetOff; { for(; i < fit; i += 4, bI += 4, cI += 4, targetI += 4) { target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); target[targetI + 1] = (double) (easing.ease(t, b[bI + 1], c[cI + 1], d)); target[targetI + 2] = (double) (easing.ease(t, b[bI + 2], c[cI + 2], d)); target[targetI + 3] = (double) (easing.ease(t, b[bI + 3], c[cI + 3], d)); } for(; i < len; i++, bI++, cI++, targetI++) target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); } return target; }
	public static double[] ease(float t, float d, float[] b, int bOff, float[] c, int cOff, Easing easing, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(bOff, b.length, len); ArrayUtils.assertIndex(cOff, c.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int bI = bOff; int cI = cOff; int targetI = targetOff; { for(; i < fit; i += 4, bI += 4, cI += 4, targetI += 4) { target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); target[targetI + 1] = (double) (easing.ease(t, b[bI + 1], c[cI + 1], d)); target[targetI + 2] = (double) (easing.ease(t, b[bI + 2], c[cI + 2], d)); target[targetI + 3] = (double) (easing.ease(t, b[bI + 3], c[cI + 3], d)); } for(; i < len; i++, bI++, cI++, targetI++) target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); } return target; }
	public static double[] ease(double t, double d, double[] b, int bOff, double[] c, int cOff, Easing easing, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(bOff, b.length, len); ArrayUtils.assertIndex(cOff, c.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int bI = bOff; int cI = cOff; int targetI = targetOff; { for(; i < fit; i += 4, bI += 4, cI += 4, targetI += 4) { target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); target[targetI + 1] = (double) (easing.ease(t, b[bI + 1], c[cI + 1], d)); target[targetI + 2] = (double) (easing.ease(t, b[bI + 2], c[cI + 2], d)); target[targetI + 3] = (double) (easing.ease(t, b[bI + 3], c[cI + 3], d)); } for(; i < len; i++, bI++, cI++, targetI++) target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); } return target; }
	public static double[] ease(char t, char d, char[] b, int bOff, char[] c, int cOff, Easing easing, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(bOff, b.length, len); ArrayUtils.assertIndex(cOff, c.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int bI = bOff; int cI = cOff; int targetI = targetOff; { for(; i < fit; i += 4, bI += 4, cI += 4, targetI += 4) { target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); target[targetI + 1] = (double) (easing.ease(t, b[bI + 1], c[cI + 1], d)); target[targetI + 2] = (double) (easing.ease(t, b[bI + 2], c[cI + 2], d)); target[targetI + 3] = (double) (easing.ease(t, b[bI + 3], c[cI + 3], d)); } for(; i < len; i++, bI++, cI++, targetI++) target[targetI + 0] = (double) (easing.ease(t, b[bI + 0], c[cI + 0], d)); } return target; }
	public static double[] ease(int t, int d, int[] b, int bOff, int[] c, int cOff, Easing easing, double[] target, int targetOff) { return ease(t, d, b, bOff, c, cOff, easing, target, targetOff, target.length - targetOff); }
	public static double[] ease(long t, long d, long[] b, int bOff, long[] c, int cOff, Easing easing, double[] target, int targetOff) { return ease(t, d, b, bOff, c, cOff, easing, target, targetOff, target.length - targetOff); }
	public static double[] ease(short t, short d, short[] b, int bOff, short[] c, int cOff, Easing easing, double[] target, int targetOff) { return ease(t, d, b, bOff, c, cOff, easing, target, targetOff, target.length - targetOff); }
	public static double[] ease(float t, float d, float[] b, int bOff, float[] c, int cOff, Easing easing, double[] target, int targetOff) { return ease(t, d, b, bOff, c, cOff, easing, target, targetOff, target.length - targetOff); }
	public static double[] ease(double t, double d, double[] b, int bOff, double[] c, int cOff, Easing easing, double[] target, int targetOff) { return ease(t, d, b, bOff, c, cOff, easing, target, targetOff, target.length - targetOff); }
	public static double[] ease(char t, char d, char[] b, int bOff, char[] c, int cOff, Easing easing, double[] target, int targetOff) { return ease(t, d, b, bOff, c, cOff, easing, target, targetOff, target.length - targetOff); }
	public static double[] ease(int t, int d, int[] b, int[] c, Easing easing, double[] target) { return ease(t, d, b, 0, c, 0, easing, target, 0); }
	public static double[] ease(long t, long d, long[] b, long[] c, Easing easing, double[] target) { return ease(t, d, b, 0, c, 0, easing, target, 0); }
	public static double[] ease(short t, short d, short[] b, short[] c, Easing easing, double[] target) { return ease(t, d, b, 0, c, 0, easing, target, 0); }
	public static double[] ease(float t, float d, float[] b, float[] c, Easing easing, double[] target) { return ease(t, d, b, 0, c, 0, easing, target, 0); }
	public static double[] ease(double t, double d, double[] b, double[] c, Easing easing, double[] target) { return ease(t, d, b, 0, c, 0, easing, target, 0); }
	public static double[] ease(char t, char d, char[] b, char[] c, Easing easing, double[] target) { return ease(t, d, b, 0, c, 0, easing, target, 0); }
	public static double[] ease(int t, int d, int[] b, int[] c, Easing easing) { return ease(t, d, b, c, easing, new double[(b.length + c.length) / 2]); }
	public static double[] ease(long t, long d, long[] b, long[] c, Easing easing) { return ease(t, d, b, c, easing, new double[(b.length + c.length) / 2]); }
	public static double[] ease(short t, short d, short[] b, short[] c, Easing easing) { return ease(t, d, b, c, easing, new double[(b.length + c.length) / 2]); }
	public static double[] ease(float t, float d, float[] b, float[] c, Easing easing) { return ease(t, d, b, c, easing, new double[(b.length + c.length) / 2]); }
	public static double[] ease(double t, double d, double[] b, double[] c, Easing easing) { return ease(t, d, b, c, easing, new double[(b.length + c.length) / 2]); }
	public static double[] ease(char t, char d, char[] b, char[] c, Easing easing) { return ease(t, d, b, c, easing, new double[(b.length + c.length) / 2]); }

	// length
	public static double length(int[] x) { int sum = 0; for(int b : x) sum += pow(b, 2); return sqrt(sum); }
	public static double length(long[] x) { long sum = 0; for(long b : x) sum += pow(b, 2); return sqrt(sum); }
	public static double length(short[] x) { short sum = 0; for(short b : x) sum += pow(b, 2); return sqrt(sum); }
	public static double length(float[] x) { float sum = 0; for(float b : x) sum += pow(b, 2); return sqrt(sum); }
	public static double length(double[] x) { double sum = 0; for(double b : x) sum += pow(b, 2); return sqrt(sum); }
	public static double length(char[] x) { char sum = 0; for(char b : x) sum += pow(b, 2); return sqrt(sum); }

	// distance
	public static double distance(double p0, double p1) { return Math.sqrt(Math.pow(p0, 2) + Math.pow(p1, 2)); }
	public static double[] distance(int[] p0, int p0Off, int[] p1, int p1Off, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(p0Off, p0.length, len); ArrayUtils.assertIndex(p1Off, p1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int p0I = p0Off; int p1I = p1Off; int targetI = targetOff; { for(; i < fit; i += 4, p0I += 4, p1I += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); target[targetI + 1] = (double) (Math.sqrt(Math.pow(p0[p0Off + 1], 2) + Math.pow(p1[p1Off + 1], 2))); target[targetI + 2] = (double) (Math.sqrt(Math.pow(p0[p0Off + 2], 2) + Math.pow(p1[p1Off + 2], 2))); target[targetI + 3] = (double) (Math.sqrt(Math.pow(p0[p0Off + 3], 2) + Math.pow(p1[p1Off + 3], 2))); } for(; i < len; i++, p0I++, p1I++, targetI++) target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); } return target; }
	public static double[] distance(long[] p0, int p0Off, long[] p1, int p1Off, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(p0Off, p0.length, len); ArrayUtils.assertIndex(p1Off, p1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int p0I = p0Off; int p1I = p1Off; int targetI = targetOff; { for(; i < fit; i += 4, p0I += 4, p1I += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); target[targetI + 1] = (double) (Math.sqrt(Math.pow(p0[p0Off + 1], 2) + Math.pow(p1[p1Off + 1], 2))); target[targetI + 2] = (double) (Math.sqrt(Math.pow(p0[p0Off + 2], 2) + Math.pow(p1[p1Off + 2], 2))); target[targetI + 3] = (double) (Math.sqrt(Math.pow(p0[p0Off + 3], 2) + Math.pow(p1[p1Off + 3], 2))); } for(; i < len; i++, p0I++, p1I++, targetI++) target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); } return target; }
	public static double[] distance(short[] p0, int p0Off, short[] p1, int p1Off, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(p0Off, p0.length, len); ArrayUtils.assertIndex(p1Off, p1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int p0I = p0Off; int p1I = p1Off; int targetI = targetOff; { for(; i < fit; i += 4, p0I += 4, p1I += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); target[targetI + 1] = (double) (Math.sqrt(Math.pow(p0[p0Off + 1], 2) + Math.pow(p1[p1Off + 1], 2))); target[targetI + 2] = (double) (Math.sqrt(Math.pow(p0[p0Off + 2], 2) + Math.pow(p1[p1Off + 2], 2))); target[targetI + 3] = (double) (Math.sqrt(Math.pow(p0[p0Off + 3], 2) + Math.pow(p1[p1Off + 3], 2))); } for(; i < len; i++, p0I++, p1I++, targetI++) target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); } return target; }
	public static double[] distance(float[] p0, int p0Off, float[] p1, int p1Off, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(p0Off, p0.length, len); ArrayUtils.assertIndex(p1Off, p1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int p0I = p0Off; int p1I = p1Off; int targetI = targetOff; { for(; i < fit; i += 4, p0I += 4, p1I += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); target[targetI + 1] = (double) (Math.sqrt(Math.pow(p0[p0Off + 1], 2) + Math.pow(p1[p1Off + 1], 2))); target[targetI + 2] = (double) (Math.sqrt(Math.pow(p0[p0Off + 2], 2) + Math.pow(p1[p1Off + 2], 2))); target[targetI + 3] = (double) (Math.sqrt(Math.pow(p0[p0Off + 3], 2) + Math.pow(p1[p1Off + 3], 2))); } for(; i < len; i++, p0I++, p1I++, targetI++) target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); } return target; }
	public static double[] distance(double[] p0, int p0Off, double[] p1, int p1Off, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(p0Off, p0.length, len); ArrayUtils.assertIndex(p1Off, p1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int p0I = p0Off; int p1I = p1Off; int targetI = targetOff; { for(; i < fit; i += 4, p0I += 4, p1I += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); target[targetI + 1] = (double) (Math.sqrt(Math.pow(p0[p0Off + 1], 2) + Math.pow(p1[p1Off + 1], 2))); target[targetI + 2] = (double) (Math.sqrt(Math.pow(p0[p0Off + 2], 2) + Math.pow(p1[p1Off + 2], 2))); target[targetI + 3] = (double) (Math.sqrt(Math.pow(p0[p0Off + 3], 2) + Math.pow(p1[p1Off + 3], 2))); } for(; i < len; i++, p0I++, p1I++, targetI++) target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); } return target; }
	public static double[] distance(char[] p0, int p0Off, char[] p1, int p1Off, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(p0Off, p0.length, len); ArrayUtils.assertIndex(p1Off, p1.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int p0I = p0Off; int p1I = p1Off; int targetI = targetOff; { for(; i < fit; i += 4, p0I += 4, p1I += 4, targetI += 4) { target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); target[targetI + 1] = (double) (Math.sqrt(Math.pow(p0[p0Off + 1], 2) + Math.pow(p1[p1Off + 1], 2))); target[targetI + 2] = (double) (Math.sqrt(Math.pow(p0[p0Off + 2], 2) + Math.pow(p1[p1Off + 2], 2))); target[targetI + 3] = (double) (Math.sqrt(Math.pow(p0[p0Off + 3], 2) + Math.pow(p1[p1Off + 3], 2))); } for(; i < len; i++, p0I++, p1I++, targetI++) target[targetI + 0] = (double) (Math.sqrt(Math.pow(p0[p0Off + 0], 2) + Math.pow(p1[p1Off + 0], 2))); } return target; }
	public static double[] distance(int[] p0, int p0Off, int[] p1, int p1Off, double[] target, int targetOff) { return distance(p0, p0Off, p1, p1Off, target, targetOff, target.length - targetOff); }
	public static double[] distance(long[] p0, int p0Off, long[] p1, int p1Off, double[] target, int targetOff) { return distance(p0, p0Off, p1, p1Off, target, targetOff, target.length - targetOff); }
	public static double[] distance(short[] p0, int p0Off, short[] p1, int p1Off, double[] target, int targetOff) { return distance(p0, p0Off, p1, p1Off, target, targetOff, target.length - targetOff); }
	public static double[] distance(float[] p0, int p0Off, float[] p1, int p1Off, double[] target, int targetOff) { return distance(p0, p0Off, p1, p1Off, target, targetOff, target.length - targetOff); }
	public static double[] distance(double[] p0, int p0Off, double[] p1, int p1Off, double[] target, int targetOff) { return distance(p0, p0Off, p1, p1Off, target, targetOff, target.length - targetOff); }
	public static double[] distance(char[] p0, int p0Off, char[] p1, int p1Off, double[] target, int targetOff) { return distance(p0, p0Off, p1, p1Off, target, targetOff, target.length - targetOff); }
	public static double[] distance(int[] p0, int[] p1, double[] target) { return distance(p0, 0, p1, 0, target, 0); }
	public static double[] distance(long[] p0, long[] p1, double[] target) { return distance(p0, 0, p1, 0, target, 0); }
	public static double[] distance(short[] p0, short[] p1, double[] target) { return distance(p0, 0, p1, 0, target, 0); }
	public static double[] distance(float[] p0, float[] p1, double[] target) { return distance(p0, 0, p1, 0, target, 0); }
	public static double[] distance(double[] p0, double[] p1, double[] target) { return distance(p0, 0, p1, 0, target, 0); }
	public static double[] distance(char[] p0, char[] p1, double[] target) { return distance(p0, 0, p1, 0, target, 0); }
	public static double[] distance(int[] p0, int[] p1) { return distance(p0, p1, new double[(p0.length + p1.length) / 2]); }
	public static double[] distance(long[] p0, long[] p1) { return distance(p0, p1, new double[(p0.length + p1.length) / 2]); }
	public static double[] distance(short[] p0, short[] p1) { return distance(p0, p1, new double[(p0.length + p1.length) / 2]); }
	public static double[] distance(float[] p0, float[] p1) { return distance(p0, p1, new double[(p0.length + p1.length) / 2]); }
	public static double[] distance(double[] p0, double[] p1) { return distance(p0, p1, new double[(p0.length + p1.length) / 2]); }
	public static double[] distance(char[] p0, char[] p1) { return distance(p0, p1, new double[(p0.length + p1.length) / 2]); }

	// dot
	public static int dot(int[] x, int xOff, int[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; int target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (int) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static long dot(long[] x, int xOff, long[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; long target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (long) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static short dot(short[] x, int xOff, short[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; short target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (short) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static float dot(float[] x, int xOff, float[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; float target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (float) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static double dot(double[] x, int xOff, double[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; double target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (double) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static char dot(char[] x, int xOff, char[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; char target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (char) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static int dot(int[] x, int xOff, int[] y, int yOff) { return dot(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static long dot(long[] x, int xOff, long[] y, int yOff) { return dot(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static short dot(short[] x, int xOff, short[] y, int yOff) { return dot(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static float dot(float[] x, int xOff, float[] y, int yOff) { return dot(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static double dot(double[] x, int xOff, double[] y, int yOff) { return dot(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static char dot(char[] x, int xOff, char[] y, int yOff) { return dot(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static int dot(int[] x, int[] y) { return dot(x, 0, y, 0); }
	public static long dot(long[] x, long[] y) { return dot(x, 0, y, 0); }
	public static short dot(short[] x, short[] y) { return dot(x, 0, y, 0); }
	public static float dot(float[] x, float[] y) { return dot(x, 0, y, 0); }
	public static double dot(double[] x, double[] y) { return dot(x, 0, y, 0); }
	public static char dot(char[] x, char[] y) { return dot(x, 0, y, 0); }

	public static double dotD(int[] x, int xOff, int[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; double target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (double) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static double dotD(long[] x, int xOff, long[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; double target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (double) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static double dotD(short[] x, int xOff, short[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; double target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (double) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static double dotD(float[] x, int xOff, float[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; double target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (double) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static double dotD(double[] x, int xOff, double[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; double target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (double) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static double dotD(char[] x, int xOff, char[] y, int yOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); int fit = (len / 4) * 4; double target = 0; int i = 0; int xI = xOff; int yI = yOff; for(; i < fit; i += 4, xI += 4, yI += 4) { target += (double) ((x[xI + 0] * y[yI + 0]) + (x[xI + 1] * y[yI + 1]) + (x[xI + 2] * y[yI + 2]) + (x[xI + 3] * y[yI + 3])); } for(; i < len; i++, xI++, yI++) target += (x[xI + 0] * y[yI + 0]); return target; }
	public static double dotD(int[] x, int xOff, int[] y, int yOff) { return dotD(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static double dotD(long[] x, int xOff, long[] y, int yOff) { return dotD(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static double dotD(short[] x, int xOff, short[] y, int yOff) { return dotD(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static double dotD(float[] x, int xOff, float[] y, int yOff) { return dotD(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static double dotD(double[] x, int xOff, double[] y, int yOff) { return dotD(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static double dotD(char[] x, int xOff, char[] y, int yOff) { return dotD(x, xOff, y, yOff, ((x.length - xOff) + (y.length - yOff)) / 2); }
	public static double dotD(int[] x, int[] y) { return dotD(x, 0, y, 0); }
	public static double dotD(long[] x, long[] y) { return dotD(x, 0, y, 0); }
	public static double dotD(short[] x, short[] y) { return dotD(x, 0, y, 0); }
	public static double dotD(float[] x, float[] y) { return dotD(x, 0, y, 0); }
	public static double dotD(double[] x, double[] y) { return dotD(x, 0, y, 0); }
	public static double dotD(char[] x, char[] y) { return dotD(x, 0, y, 0); }

	// cross
	public static int[] cross(int xx, int xy, int xz, int yx, int yy, int yz, int[] target) { assert target.length != 3 : "Length is not acceptable!"; target[0] = xy * yz - yy * xz; target[1] = yx * xz - xx * yz; target[2] = xx * yy - yx * xy; return target; }
	public static long[] cross(long xx, long xy, long xz, long yx, long yy, long yz, long[] target) { assert target.length != 3 : "Length is not acceptable!"; target[0] = xy * yz - yy * xz; target[1] = yx * xz - xx * yz; target[2] = xx * yy - yx * xy; return target; }
	public static short[] cross(short xx, short xy, short xz, short yx, short yy, short yz, short[] target) { assert target.length != 3 : "Length is not acceptable!"; target[0] = (short) (xy * yz - yy * xz); target[1] = (short) (yx * xz - xx * yz); target[2] = (short) (xx * yy - yx * xy); return target; }
	public static float[] cross(float xx, float xy, float xz, float yx, float yy, float yz, float[] target) { assert target.length != 3 : "Length is not acceptable!"; target[0] = xy * yz - yy * xz; target[1] = yx * xz - xx * yz; target[2] = xx * yy - yx * xy; return target; }
	public static double[] cross(double xx, double xy, double xz, double yx, double yy, double yz, double[] target) { assert target.length != 3 : "Length is not acceptable!"; target[0] = xy * yz - yy * xz; target[1] = yx * xz - xx * yz; target[2] = xx * yy - yx * xy; return target; }
	public static char[] cross(char xx, char xy, char xz, char yx, char yy, char yz, char[] target) { assert target.length != 3 : "Length is not acceptable!"; target[0] = (char) (xy * yz - yy * xz); target[1] = (char) (yx * xz - xx * yz); target[2] = (char) (xx * yy - yx * xy); return target; }
	public static int[] cross(int xx, int xy, int xz, int yx, int yy, int yz) { return cross(xx, xy, xz, yx, yy, yz, new int[3]); }
	public static long[] cross(long xx, long xy, long xz, long yx, long yy, long yz) { return cross(xx, xy, xz, yx, yy, yz, new long[3]); }
	public static short[] cross(short xx, short xy, short xz, short yx, short yy, short yz) { return cross(xx, xy, xz, yx, yy, yz, new short[3]); }
	public static float[] cross(float xx, float xy, float xz, float yx, float yy, float yz) { return cross(xx, xy, xz, yx, yy, yz, new float[3]); }
	public static double[] cross(double xx, double xy, double xz, double yx, double yy, double yz) { return cross(xx, xy, xz, yx, yy, yz, new double[3]); }
	public static char[] cross(char xx, char xy, char xz, char yx, char yy, char yz) { return cross(xx, xy, xz, yx, yy, yz, new char[3]); }

	public static int cross(int xx, int xy, int yx, int yy) { return xx * yy - yx * xy; }
	public static long cross(long xx, long xy, long yx, long yy) { return xx * yy - yx * xy; }
	public static short cross(short xx, short xy, short yx, short yy) { return (short) (xx * yy - yx * xy); }
	public static float cross(float xx, float xy, float yx, float yy) { return xx * yy - yx * xy; }
	public static double cross(double xx, double xy, double yx, double yy) { return xx * yy - yx * xy; }
	public static char cross(char xx, char xy, char yx, char yy) { return (char) (xx * yy - yx * xy); }

	// normalize
	public static double[] normalize(int[] x, double[] target) { assert x.length == target.length : "Length is not equal!"; double a = length(x); int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = x[i + 0] / a; target[i + 1] = x[i + 1] / a; target[i + 2] = x[i + 2] / a; target[i + 3] = x[i + 3] / a; i += 4; } for(; i < target.length; i++) target[i + 0] = x[i + 0] / a; return target; }
	public static double[] normalize(long[] x, double[] target) { assert x.length == target.length : "Length is not equal!"; double a = length(x); int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = x[i + 0] / a; target[i + 1] = x[i + 1] / a; target[i + 2] = x[i + 2] / a; target[i + 3] = x[i + 3] / a; i += 4; } for(; i < target.length; i++) target[i + 0] = x[i + 0] / a; return target; }
	public static double[] normalize(short[] x, double[] target) { assert x.length == target.length : "Length is not equal!"; double a = length(x); int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = x[i + 0] / a; target[i + 1] = x[i + 1] / a; target[i + 2] = x[i + 2] / a; target[i + 3] = x[i + 3] / a; i += 4; } for(; i < target.length; i++) target[i + 0] = x[i + 0] / a; return target; }
	public static double[] normalize(float[] x, double[] target) { assert x.length == target.length : "Length is not equal!"; double a = length(x); int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = x[i + 0] / a; target[i + 1] = x[i + 1] / a; target[i + 2] = x[i + 2] / a; target[i + 3] = x[i + 3] / a; i += 4; } for(; i < target.length; i++) target[i + 0] = x[i + 0] / a; return target; }
	public static double[] normalize(double[] x, double[] target) { assert x.length == target.length : "Length is not equal!"; double a = length(x); int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = x[i + 0] / a; target[i + 1] = x[i + 1] / a; target[i + 2] = x[i + 2] / a; target[i + 3] = x[i + 3] / a; i += 4; } for(; i < target.length; i++) target[i + 0] = x[i + 0] / a; return target; }
	public static double[] normalize(char[] x, double[] target) { assert x.length == target.length : "Length is not equal!"; double a = length(x); int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = x[i + 0] / a; target[i + 1] = x[i + 1] / a; target[i + 2] = x[i + 2] / a; target[i + 3] = x[i + 3] / a; i += 4; } for(; i < target.length; i++) target[i + 0] = x[i + 0] / a; return target; }
	public static double[] normalize(int[] x) { return normalize(x, new double[x.length]); }
	public static double[] normalize(long[] x) { return normalize(x, new double[x.length]); }
	public static double[] normalize(short[] x) { return normalize(x, new double[x.length]); }
	public static double[] normalize(float[] x) { return normalize(x, new double[x.length]); }
	public static double[] normalize(double[] x) { return normalize(x, new double[x.length]); }
	public static double[] normalize(char[] x) { return normalize(x, new double[x.length]); }

	// refract
	protected static double refract(double I, double N, double eta, double dot) { double k = 1.0 - eta * eta * (1.0 - dot * dot); return k < 0 ? 0 : eta * I - (eta * dot + sqrt(k)) * N; }
	public static double[] refract(int[] I, int IOff, int[] N, int NOff, int eta, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(IOff, I.length, len); ArrayUtils.assertIndex(NOff, N.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); double dot = dotD(I, IOff, N, NOff); int fit = (len / 4) * 4; int i = 0; int II = IOff; int NI = NOff; int targetI = targetOff; { for(; i < fit; i += 4, II += 4, NI += 4, targetI += 4) { target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); target[targetI + 1] = (double) refract(I[II + 1], N[NI + 1], eta, dot); target[targetI + 2] = (double) refract(I[II + 2], N[NI + 2], eta, dot); target[targetI + 3] = (double) refract(I[II + 3], N[NI + 3], eta, dot); } for(; i < len; i++, II++, NI++, targetI++) target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); } return target; }
	public static double[] refract(long[] I, int IOff, long[] N, int NOff, long eta, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(IOff, I.length, len); ArrayUtils.assertIndex(NOff, N.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); double dot = dotD(I, IOff, N, NOff); int fit = (len / 4) * 4; int i = 0; int II = IOff; int NI = NOff; int targetI = targetOff; { for(; i < fit; i += 4, II += 4, NI += 4, targetI += 4) { target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); target[targetI + 1] = (double) refract(I[II + 1], N[NI + 1], eta, dot); target[targetI + 2] = (double) refract(I[II + 2], N[NI + 2], eta, dot); target[targetI + 3] = (double) refract(I[II + 3], N[NI + 3], eta, dot); } for(; i < len; i++, II++, NI++, targetI++) target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); } return target; }
	public static double[] refract(short[] I, int IOff, short[] N, int NOff, short eta, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(IOff, I.length, len); ArrayUtils.assertIndex(NOff, N.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); double dot = dotD(I, IOff, N, NOff); int fit = (len / 4) * 4; int i = 0; int II = IOff; int NI = NOff; int targetI = targetOff; { for(; i < fit; i += 4, II += 4, NI += 4, targetI += 4) { target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); target[targetI + 1] = (double) refract(I[II + 1], N[NI + 1], eta, dot); target[targetI + 2] = (double) refract(I[II + 2], N[NI + 2], eta, dot); target[targetI + 3] = (double) refract(I[II + 3], N[NI + 3], eta, dot); } for(; i < len; i++, II++, NI++, targetI++) target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); } return target; }
	public static double[] refract(float[] I, int IOff, float[] N, int NOff, float eta, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(IOff, I.length, len); ArrayUtils.assertIndex(NOff, N.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); double dot = dotD(I, IOff, N, NOff); int fit = (len / 4) * 4; int i = 0; int II = IOff; int NI = NOff; int targetI = targetOff; { for(; i < fit; i += 4, II += 4, NI += 4, targetI += 4) { target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); target[targetI + 1] = (double) refract(I[II + 1], N[NI + 1], eta, dot); target[targetI + 2] = (double) refract(I[II + 2], N[NI + 2], eta, dot); target[targetI + 3] = (double) refract(I[II + 3], N[NI + 3], eta, dot); } for(; i < len; i++, II++, NI++, targetI++) target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); } return target; }
	public static double[] refract(double[] I, int IOff, double[] N, int NOff, double eta, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(IOff, I.length, len); ArrayUtils.assertIndex(NOff, N.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); double dot = dotD(I, IOff, N, NOff); int fit = (len / 4) * 4; int i = 0; int II = IOff; int NI = NOff; int targetI = targetOff; { for(; i < fit; i += 4, II += 4, NI += 4, targetI += 4) { target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); target[targetI + 1] = (double) refract(I[II + 1], N[NI + 1], eta, dot); target[targetI + 2] = (double) refract(I[II + 2], N[NI + 2], eta, dot); target[targetI + 3] = (double) refract(I[II + 3], N[NI + 3], eta, dot); } for(; i < len; i++, II++, NI++, targetI++) target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); } return target; }
	public static double[] refract(char[] I, int IOff, char[] N, int NOff, char eta, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(IOff, I.length, len); ArrayUtils.assertIndex(NOff, N.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); double dot = dotD(I, IOff, N, NOff); int fit = (len / 4) * 4; int i = 0; int II = IOff; int NI = NOff; int targetI = targetOff; { for(; i < fit; i += 4, II += 4, NI += 4, targetI += 4) { target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); target[targetI + 1] = (double) refract(I[II + 1], N[NI + 1], eta, dot); target[targetI + 2] = (double) refract(I[II + 2], N[NI + 2], eta, dot); target[targetI + 3] = (double) refract(I[II + 3], N[NI + 3], eta, dot); } for(; i < len; i++, II++, NI++, targetI++) target[targetI + 0] = (double) refract(I[II + 0], N[NI + 0], eta, dot); } return target; }
	public static double[] refract(int[] I, int IOff, int[] N, int NOff, int eta, double[] target, int targetOff) { return refract(I, IOff, N, NOff, eta, target, targetOff, target.length - targetOff); }
	public static double[] refract(long[] I, int IOff, long[] N, int NOff, long eta, double[] target, int targetOff) { return refract(I, IOff, N, NOff, eta, target, targetOff, target.length - targetOff); }
	public static double[] refract(short[] I, int IOff, short[] N, int NOff, short eta, double[] target, int targetOff) { return refract(I, IOff, N, NOff, eta, target, targetOff, target.length - targetOff); }
	public static double[] refract(float[] I, int IOff, float[] N, int NOff, float eta, double[] target, int targetOff) { return refract(I, IOff, N, NOff, eta, target, targetOff, target.length - targetOff); }
	public static double[] refract(double[] I, int IOff, double[] N, int NOff, double eta, double[] target, int targetOff) { return refract(I, IOff, N, NOff, eta, target, targetOff, target.length - targetOff); }
	public static double[] refract(char[] I, int IOff, char[] N, int NOff, char eta, double[] target, int targetOff) { return refract(I, IOff, N, NOff, eta, target, targetOff, target.length - targetOff); }
	public static double[] refract(int[] I, int[] N, int eta, double[] target) { return refract(I, 0, N, 0, eta, target, 0); }
	public static double[] refract(long[] I, long[] N, long eta, double[] target) { return refract(I, 0, N, 0, eta, target, 0); }
	public static double[] refract(short[] I, short[] N, short eta, double[] target) { return refract(I, 0, N, 0, eta, target, 0); }
	public static double[] refract(float[] I, float[] N, float eta, double[] target) { return refract(I, 0, N, 0, eta, target, 0); }
	public static double[] refract(double[] I, double[] N, double eta, double[] target) { return refract(I, 0, N, 0, eta, target, 0); }
	public static double[] refract(char[] I, char[] N, char eta, double[] target) { return refract(I, 0, N, 0, eta, target, 0); }
	public static double[] refract(int[] I, int[] N, int eta) { return refract(I, N, eta, new double[(I.length + N.length) / 2]); }
	public static double[] refract(long[] I, long[] N, long eta) { return refract(I, N, eta, new double[(I.length + N.length) / 2]); }
	public static double[] refract(short[] I, short[] N, short eta) { return refract(I, N, eta, new double[(I.length + N.length) / 2]); }
	public static double[] refract(float[] I, float[] N, float eta) { return refract(I, N, eta, new double[(I.length + N.length) / 2]); }
	public static double[] refract(double[] I, double[] N, double eta) { return refract(I, N, eta, new double[(I.length + N.length) / 2]); }
	public static double[] refract(char[] I, char[] N, char eta) { return refract(I, N, eta, new double[(I.length + N.length) / 2]); }

	// fold
	public static int fold2(int fold) { int result = 2; while(result < fold) result *= 2; return result; }
	public static long fold2(long fold) { long result = 2; while(result < fold) result *= 2; return result; }
	public static short fold2(short fold) { short result = 2; while(result < fold) result *= 2; return result; }
	public static float fold2(float fold) { float result = 2; while(result < fold) result *= 2; return result; }
	public static double fold2(double fold) { double result = 2; while(result < fold) result *= 2; return result; }
	public static char fold2(char fold) { char result = 2; while(result < fold) result *= 2; return result; }
	public static int[] fold2(int[] fold, int foldOff, int[] target, int targetOff, int len) { ArrayUtils.assertIndex(foldOff, fold.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int foldI = foldOff; int targetI = targetOff; { for(; i < fit; i += 4, foldI += 4, targetI += 4) { target[targetI + 0] = (int) fold2(fold[foldI + 0]); target[targetI + 1] = (int) fold2(fold[foldI + 1]); target[targetI + 2] = (int) fold2(fold[foldI + 2]); target[targetI + 3] = (int) fold2(fold[foldI + 3]); } for(; i < len; i++, foldI++, targetI++) target[targetI + 0] = (int) fold2(fold[foldI + 0]); } return target; }
	public static long[] fold2(long[] fold, int foldOff, long[] target, int targetOff, int len) { ArrayUtils.assertIndex(foldOff, fold.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int foldI = foldOff; int targetI = targetOff; { for(; i < fit; i += 4, foldI += 4, targetI += 4) { target[targetI + 0] = (long) fold2(fold[foldI + 0]); target[targetI + 1] = (long) fold2(fold[foldI + 1]); target[targetI + 2] = (long) fold2(fold[foldI + 2]); target[targetI + 3] = (long) fold2(fold[foldI + 3]); } for(; i < len; i++, foldI++, targetI++) target[targetI + 0] = (long) fold2(fold[foldI + 0]); } return target; }
	public static short[] fold2(short[] fold, int foldOff, short[] target, int targetOff, int len) { ArrayUtils.assertIndex(foldOff, fold.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int foldI = foldOff; int targetI = targetOff; { for(; i < fit; i += 4, foldI += 4, targetI += 4) { target[targetI + 0] = (short) fold2(fold[foldI + 0]); target[targetI + 1] = (short) fold2(fold[foldI + 1]); target[targetI + 2] = (short) fold2(fold[foldI + 2]); target[targetI + 3] = (short) fold2(fold[foldI + 3]); } for(; i < len; i++, foldI++, targetI++) target[targetI + 0] = (short) fold2(fold[foldI + 0]); } return target; }
	public static float[] fold2(float[] fold, int foldOff, float[] target, int targetOff, int len) { ArrayUtils.assertIndex(foldOff, fold.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int foldI = foldOff; int targetI = targetOff; { for(; i < fit; i += 4, foldI += 4, targetI += 4) { target[targetI + 0] = (float) fold2(fold[foldI + 0]); target[targetI + 1] = (float) fold2(fold[foldI + 1]); target[targetI + 2] = (float) fold2(fold[foldI + 2]); target[targetI + 3] = (float) fold2(fold[foldI + 3]); } for(; i < len; i++, foldI++, targetI++) target[targetI + 0] = (float) fold2(fold[foldI + 0]); } return target; }
	public static double[] fold2(double[] fold, int foldOff, double[] target, int targetOff, int len) { ArrayUtils.assertIndex(foldOff, fold.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int foldI = foldOff; int targetI = targetOff; { for(; i < fit; i += 4, foldI += 4, targetI += 4) { target[targetI + 0] = (double) fold2(fold[foldI + 0]); target[targetI + 1] = (double) fold2(fold[foldI + 1]); target[targetI + 2] = (double) fold2(fold[foldI + 2]); target[targetI + 3] = (double) fold2(fold[foldI + 3]); } for(; i < len; i++, foldI++, targetI++) target[targetI + 0] = (double) fold2(fold[foldI + 0]); } return target; }
	public static char[] fold2(char[] fold, int foldOff, char[] target, int targetOff, int len) { ArrayUtils.assertIndex(foldOff, fold.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int foldI = foldOff; int targetI = targetOff; { for(; i < fit; i += 4, foldI += 4, targetI += 4) { target[targetI + 0] = (char) fold2(fold[foldI + 0]); target[targetI + 1] = (char) fold2(fold[foldI + 1]); target[targetI + 2] = (char) fold2(fold[foldI + 2]); target[targetI + 3] = (char) fold2(fold[foldI + 3]); } for(; i < len; i++, foldI++, targetI++) target[targetI + 0] = (char) fold2(fold[foldI + 0]); } return target; }
	public static int[] fold2(int[] fold, int foldOff, int[] target, int targetOff) { return fold2(fold, foldOff, target, targetOff, target.length - targetOff); }
	public static long[] fold2(long[] fold, int foldOff, long[] target, int targetOff) { return fold2(fold, foldOff, target, targetOff, target.length - targetOff); }
	public static short[] fold2(short[] fold, int foldOff, short[] target, int targetOff) { return fold2(fold, foldOff, target, targetOff, target.length - targetOff); }
	public static float[] fold2(float[] fold, int foldOff, float[] target, int targetOff) { return fold2(fold, foldOff, target, targetOff, target.length - targetOff); }
	public static double[] fold2(double[] fold, int foldOff, double[] target, int targetOff) { return fold2(fold, foldOff, target, targetOff, target.length - targetOff); }
	public static char[] fold2(char[] fold, int foldOff, char[] target, int targetOff) { return fold2(fold, foldOff, target, targetOff, target.length - targetOff); }
	public static int[] fold2(int[] fold, int[] target) { return fold2(fold, 0, target, 0); }
	public static long[] fold2(long[] fold, long[] target) { return fold2(fold, 0, target, 0); }
	public static short[] fold2(short[] fold, short[] target) { return fold2(fold, 0, target, 0); }
	public static float[] fold2(float[] fold, float[] target) { return fold2(fold, 0, target, 0); }
	public static double[] fold2(double[] fold, double[] target) { return fold2(fold, 0, target, 0); }
	public static char[] fold2(char[] fold, char[] target) { return fold2(fold, 0, target, 0); }
	public static int[] fold2(int[] fold) { return fold2(fold, new int[(fold.length) / 1]); }
	public static long[] fold2(long[] fold) { return fold2(fold, new long[(fold.length) / 1]); }
	public static short[] fold2(short[] fold) { return fold2(fold, new short[(fold.length) / 1]); }
	public static float[] fold2(float[] fold) { return fold2(fold, new float[(fold.length) / 1]); }
	public static double[] fold2(double[] fold) { return fold2(fold, new double[(fold.length) / 1]); }
	public static char[] fold2(char[] fold) { return fold2(fold, new char[(fold.length) / 1]); }

	// less than
	public static boolean lessThan(int x, int y) { return x < y; }
	public static boolean lessThan(long x, long y) { return x < y; }
	public static boolean lessThan(short x, short y) { return x < y; }
	public static boolean lessThan(float x, float y) { return x < y; }
	public static boolean lessThan(double x, double y) { return x < y; }
	public static boolean lessThan(char x, char y) { return x < y; }
	public static boolean[] lessThan(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] < y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] < y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] < y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); } return target; }
	public static boolean[] lessThan(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] < y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] < y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] < y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); } return target; }
	public static boolean[] lessThan(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] < y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] < y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] < y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); } return target; }
	public static boolean[] lessThan(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] < y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] < y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] < y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); } return target; }
	public static boolean[] lessThan(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] < y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] < y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] < y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); } return target; }
	public static boolean[] lessThan(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] < y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] < y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] < y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] < y[yI + 0]); } return target; }
	public static boolean[] lessThan(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff) { return lessThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThan(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff) { return lessThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThan(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff) { return lessThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThan(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff) { return lessThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThan(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff) { return lessThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThan(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff) { return lessThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThan(int[] x, int[] y, boolean[] target) { return lessThan(x, 0, y, 0, target, 0); }
	public static boolean[] lessThan(long[] x, long[] y, boolean[] target) { return lessThan(x, 0, y, 0, target, 0); }
	public static boolean[] lessThan(short[] x, short[] y, boolean[] target) { return lessThan(x, 0, y, 0, target, 0); }
	public static boolean[] lessThan(float[] x, float[] y, boolean[] target) { return lessThan(x, 0, y, 0, target, 0); }
	public static boolean[] lessThan(double[] x, double[] y, boolean[] target) { return lessThan(x, 0, y, 0, target, 0); }
	public static boolean[] lessThan(char[] x, char[] y, boolean[] target) { return lessThan(x, 0, y, 0, target, 0); }
	public static boolean[] lessThan(int[] x, int[] y) { return lessThan(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] lessThan(long[] x, long[] y) { return lessThan(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] lessThan(short[] x, short[] y) { return lessThan(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] lessThan(float[] x, float[] y) { return lessThan(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] lessThan(double[] x, double[] y) { return lessThan(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] lessThan(char[] x, char[] y) { return lessThan(x, y, new boolean[(x.length + y.length) / 2]); }

	// less than equal
	public static boolean lessThanEqual(int x, int y) { return x <= y; }
	public static boolean lessThanEqual(long x, long y) { return x <= y; }
	public static boolean lessThanEqual(short x, short y) { return x <= y; }
	public static boolean lessThanEqual(float x, float y) { return x <= y; }
	public static boolean lessThanEqual(double x, double y) { return x <= y; }
	public static boolean lessThanEqual(char x, char y) { return x <= y; }
	public static boolean[] lessThanEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] <= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] <= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] <= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); } return target; }
	public static boolean[] lessThanEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] <= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] <= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] <= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); } return target; }
	public static boolean[] lessThanEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] <= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] <= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] <= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); } return target; }
	public static boolean[] lessThanEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] <= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] <= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] <= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); } return target; }
	public static boolean[] lessThanEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] <= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] <= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] <= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); } return target; }
	public static boolean[] lessThanEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] <= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] <= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] <= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] <= y[yI + 0]); } return target; }
	public static boolean[] lessThanEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff) { return lessThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThanEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff) { return lessThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThanEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff) { return lessThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThanEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff) { return lessThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThanEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff) { return lessThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThanEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff) { return lessThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] lessThanEqual(int[] x, int[] y, boolean[] target) { return lessThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] lessThanEqual(long[] x, long[] y, boolean[] target) { return lessThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] lessThanEqual(short[] x, short[] y, boolean[] target) { return lessThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] lessThanEqual(float[] x, float[] y, boolean[] target) { return lessThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] lessThanEqual(double[] x, double[] y, boolean[] target) { return lessThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] lessThanEqual(char[] x, char[] y, boolean[] target) { return lessThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] lessThanEqual(int[] x, int[] y) { return lessThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] lessThanEqual(long[] x, long[] y) { return lessThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] lessThanEqual(short[] x, short[] y) { return lessThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] lessThanEqual(float[] x, float[] y) { return lessThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] lessThanEqual(double[] x, double[] y) { return lessThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] lessThanEqual(char[] x, char[] y) { return lessThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }

	// greater than
	public static boolean greaterThan(int x, int y) { return x > y; }
	public static boolean greaterThan(long x, long y) { return x > y; }
	public static boolean greaterThan(short x, short y) { return x > y; }
	public static boolean greaterThan(float x, float y) { return x > y; }
	public static boolean greaterThan(double x, double y) { return x > y; }
	public static boolean greaterThan(char x, char y) { return x > y; }
	public static boolean[] greaterThan(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] > y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] > y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] > y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); } return target; }
	public static boolean[] greaterThan(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] > y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] > y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] > y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); } return target; }
	public static boolean[] greaterThan(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] > y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] > y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] > y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); } return target; }
	public static boolean[] greaterThan(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] > y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] > y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] > y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); } return target; }
	public static boolean[] greaterThan(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] > y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] > y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] > y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); } return target; }
	public static boolean[] greaterThan(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] > y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] > y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] > y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] > y[yI + 0]); } return target; }
	public static boolean[] greaterThan(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff) { return greaterThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThan(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff) { return greaterThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThan(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff) { return greaterThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThan(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff) { return greaterThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThan(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff) { return greaterThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThan(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff) { return greaterThan(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThan(int[] x, int[] y, boolean[] target) { return greaterThan(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThan(long[] x, long[] y, boolean[] target) { return greaterThan(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThan(short[] x, short[] y, boolean[] target) { return greaterThan(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThan(float[] x, float[] y, boolean[] target) { return greaterThan(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThan(double[] x, double[] y, boolean[] target) { return greaterThan(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThan(char[] x, char[] y, boolean[] target) { return greaterThan(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThan(int[] x, int[] y) { return greaterThan(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] greaterThan(long[] x, long[] y) { return greaterThan(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] greaterThan(short[] x, short[] y) { return greaterThan(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] greaterThan(float[] x, float[] y) { return greaterThan(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] greaterThan(double[] x, double[] y) { return greaterThan(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] greaterThan(char[] x, char[] y) { return greaterThan(x, y, new boolean[(x.length + y.length) / 2]); }

	// greater than equal
	public static boolean greaterThanEqual(int x, int y) { return x >= y; }
	public static boolean greaterThanEqual(long x, long y) { return x >= y; }
	public static boolean greaterThanEqual(short x, short y) { return x >= y; }
	public static boolean greaterThanEqual(float x, float y) { return x >= y; }
	public static boolean greaterThanEqual(double x, double y) { return x >= y; }
	public static boolean greaterThanEqual(char x, char y) { return x >= y; }
	public static boolean[] greaterThanEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] >= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] >= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] >= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); } return target; }
	public static boolean[] greaterThanEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] >= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] >= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] >= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); } return target; }
	public static boolean[] greaterThanEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] >= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] >= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] >= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); } return target; }
	public static boolean[] greaterThanEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] >= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] >= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] >= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); } return target; }
	public static boolean[] greaterThanEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] >= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] >= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] >= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); } return target; }
	public static boolean[] greaterThanEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] >= y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] >= y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] >= y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] >= y[yI + 0]); } return target; }
	public static boolean[] greaterThanEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff) { return greaterThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThanEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff) { return greaterThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThanEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff) { return greaterThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThanEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff) { return greaterThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThanEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff) { return greaterThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThanEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff) { return greaterThanEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] greaterThanEqual(int[] x, int[] y, boolean[] target) { return greaterThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThanEqual(long[] x, long[] y, boolean[] target) { return greaterThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThanEqual(short[] x, short[] y, boolean[] target) { return greaterThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThanEqual(float[] x, float[] y, boolean[] target) { return greaterThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThanEqual(double[] x, double[] y, boolean[] target) { return greaterThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThanEqual(char[] x, char[] y, boolean[] target) { return greaterThanEqual(x, 0, y, 0, target, 0); }
	public static boolean[] greaterThanEqual(int[] x, int[] y) { return greaterThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] greaterThanEqual(long[] x, long[] y) { return greaterThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] greaterThanEqual(short[] x, short[] y) { return greaterThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] greaterThanEqual(float[] x, float[] y) { return greaterThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] greaterThanEqual(double[] x, double[] y) { return greaterThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] greaterThanEqual(char[] x, char[] y) { return greaterThanEqual(x, y, new boolean[(x.length + y.length) / 2]); }

	// equal
	public static boolean equal(int x, int y) { return x == y; }
	public static boolean equal(long x, long y) { return x == y; }
	public static boolean equal(short x, short y) { return x == y; }
	public static boolean equal(float x, float y) { return x == y; }
	public static boolean equal(double x, double y) { return x == y; }
	public static boolean equal(char x, char y) { return x == y; }
	public static boolean[] equal(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] == y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] == y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] == y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); } return target; }
	public static boolean[] equal(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] == y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] == y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] == y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); } return target; }
	public static boolean[] equal(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] == y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] == y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] == y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); } return target; }
	public static boolean[] equal(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] == y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] == y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] == y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); } return target; }
	public static boolean[] equal(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] == y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] == y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] == y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); } return target; }
	public static boolean[] equal(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] == y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] == y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] == y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] == y[yI + 0]); } return target; }
	public static boolean[] equal(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff) { return equal(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] equal(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff) { return equal(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] equal(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff) { return equal(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] equal(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff) { return equal(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] equal(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff) { return equal(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] equal(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff) { return equal(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] equal(int[] x, int[] y, boolean[] target) { return equal(x, 0, y, 0, target, 0); }
	public static boolean[] equal(long[] x, long[] y, boolean[] target) { return equal(x, 0, y, 0, target, 0); }
	public static boolean[] equal(short[] x, short[] y, boolean[] target) { return equal(x, 0, y, 0, target, 0); }
	public static boolean[] equal(float[] x, float[] y, boolean[] target) { return equal(x, 0, y, 0, target, 0); }
	public static boolean[] equal(double[] x, double[] y, boolean[] target) { return equal(x, 0, y, 0, target, 0); }
	public static boolean[] equal(char[] x, char[] y, boolean[] target) { return equal(x, 0, y, 0, target, 0); }
	public static boolean[] equal(int[] x, int[] y) { return equal(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] equal(long[] x, long[] y) { return equal(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] equal(short[] x, short[] y) { return equal(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] equal(float[] x, float[] y) { return equal(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] equal(double[] x, double[] y) { return equal(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] equal(char[] x, char[] y) { return equal(x, y, new boolean[(x.length + y.length) / 2]); }

	// not equal
	public static boolean notEqual(int x, int y) { return x != y; }
	public static boolean notEqual(long x, long y) { return x != y; }
	public static boolean notEqual(short x, short y) { return x != y; }
	public static boolean notEqual(float x, float y) { return x != y; }
	public static boolean notEqual(double x, double y) { return x != y; }
	public static boolean notEqual(char x, char y) { return x != y; }
	public static boolean[] notEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] != y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] != y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] != y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); } return target; }
	public static boolean[] notEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] != y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] != y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] != y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); } return target; }
	public static boolean[] notEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] != y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] != y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] != y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); } return target; }
	public static boolean[] notEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] != y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] != y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] != y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); } return target; }
	public static boolean[] notEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] != y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] != y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] != y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); } return target; }
	public static boolean[] notEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len) { ArrayUtils.assertIndex(xOff, x.length, len); ArrayUtils.assertIndex(yOff, y.length, len); ArrayUtils.assertIndex(targetOff, target.length, len); int fit = (len / 4) * 4; int i = 0; int xI = xOff; int yI = yOff; int targetI = targetOff; { for(; i < fit; i += 4, xI += 4, yI += 4, targetI += 4) { target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); target[targetI + 1] = (boolean) (x[xI + 1] != y[yI + 1]); target[targetI + 2] = (boolean) (x[xI + 2] != y[yI + 2]); target[targetI + 3] = (boolean) (x[xI + 3] != y[yI + 3]); } for(; i < len; i++, xI++, yI++, targetI++) target[targetI + 0] = (boolean) (x[xI + 0] != y[yI + 0]); } return target; }
	public static boolean[] notEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff) { return notEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] notEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff) { return notEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] notEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff) { return notEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] notEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff) { return notEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] notEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff) { return notEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] notEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff) { return notEqual(x, xOff, y, yOff, target, targetOff, target.length - targetOff); }
	public static boolean[] notEqual(int[] x, int[] y, boolean[] target) { return notEqual(x, 0, y, 0, target, 0); }
	public static boolean[] notEqual(long[] x, long[] y, boolean[] target) { return notEqual(x, 0, y, 0, target, 0); }
	public static boolean[] notEqual(short[] x, short[] y, boolean[] target) { return notEqual(x, 0, y, 0, target, 0); }
	public static boolean[] notEqual(float[] x, float[] y, boolean[] target) { return notEqual(x, 0, y, 0, target, 0); }
	public static boolean[] notEqual(double[] x, double[] y, boolean[] target) { return notEqual(x, 0, y, 0, target, 0); }
	public static boolean[] notEqual(char[] x, char[] y, boolean[] target) { return notEqual(x, 0, y, 0, target, 0); }
	public static boolean[] notEqual(int[] x, int[] y) { return notEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] notEqual(long[] x, long[] y) { return notEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] notEqual(short[] x, short[] y) { return notEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] notEqual(float[] x, float[] y) { return notEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] notEqual(double[] x, double[] y) { return notEqual(x, y, new boolean[(x.length + y.length) / 2]); }
	public static boolean[] notEqual(char[] x, char[] y) { return notEqual(x, y, new boolean[(x.length + y.length) / 2]); }

	// primitive
	public static int primitive(Integer x, int fallback) { if(x == null) return fallback; return x; }
	public static long primitive(Long x, long fallback) { if(x == null) return fallback; return x; }
	public static short primitive(Short x, short fallback) { if(x == null) return fallback; return x; }
	public static float primitive(Float x, float fallback) { if(x == null) return fallback; return x; }
	public static double primitive(Double x, double fallback) { if(x == null) return fallback; return x; }
	public static char primitive(Character x, char fallback) { if(x == null) return fallback; return x; }
	public static int primitive(Integer x) { if(x == null) throw new NullPointerException(); return x; }
	public static long primitive(Long x) { if(x == null) throw new NullPointerException(); return x; }
	public static short primitive(Short x) { if(x == null) throw new NullPointerException(); return x; }
	public static float primitive(Float x) { if(x == null) throw new NullPointerException(); return x; }
	public static double primitive(Double x) { if(x == null) throw new NullPointerException(); return x; }
	public static char primitive(Character x) { if(x == null) throw new NullPointerException(); return x; }
	public static int[] primitive(Integer[] x, int fallback, int[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0], fallback); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0], fallback); return target; }
	public static long[] primitive(Long[] x, long fallback, long[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0], fallback); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0], fallback); return target; }
	public static short[] primitive(Short[] x, short fallback, short[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0], fallback); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0], fallback); return target; }
	public static float[] primitive(Float[] x, float fallback, float[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0], fallback); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0], fallback); return target; }
	public static double[] primitive(Double[] x, double fallback, double[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0], fallback); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0], fallback); return target; }
	public static char[] primitive(Character[] x, char fallback, char[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0], fallback); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0], fallback); return target; }
	public static int[] primitive(Integer[] x, int[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0]); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0]); return target; }
	public static long[] primitive(Long[] x, long[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0]); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0]); return target; }
	public static short[] primitive(Short[] x, short[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0]); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0]); return target; }
	public static float[] primitive(Float[] x, float[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0]); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0]); return target; }
	public static double[] primitive(Double[] x, double[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0]); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0]); return target; }
	public static char[] primitive(Character[] x, char[] target) { assert x.length == target.length : "Length is not notEqual!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i + 0] = primitive(x[i + 0]); target[i + 1] = primitive(x[i + 1]); target[i + 2] = primitive(x[i + 2]); target[i + 3] = primitive(x[i + 3]); i += 4; } for(; i < target.length; i++) target[i + 0] = primitive(x[i + 0]); return target; }

	public static int[] getFractionValue(double x) { int zeroes = 0; while(x % 1 != 0) { x = x * 10; zeroes++; } return new int[] { (int) x, (int) Math.pow(10, zeroes)}; }
	public static int[] getFractionValue(float x) { int zeroes = 0; while(x % 1 != 0) { x = x * 10; zeroes++; } return new int[] { (int) x, (int) Math.pow(10, zeroes)}; }
	public static int[] getSimplestFractionValue(double x) { int[] fractions = getFractionValue(x); int gcd = gcd(fractions[0], fractions[1]); fractions[0] /= gcd; fractions[1] /= gcd; return fractions; }
	public static int[] getSimplestFractionValue(float x) { int[] fractions = getFractionValue(x); int gcd = gcd(fractions[0], fractions[1]); fractions[0] /= gcd; fractions[1] /= gcd; return fractions; }
	public static int gcd(int x, int y) { while(y != 0) { int temp = y; y = x % y; x = temp; } return x; }
	public static long gcd(long x, long y) { while(y != 0) { long temp = y; y = x % y; x = temp; } return x; }
	public static short gcd(short x, short y) { while(y != 0) { short temp = y; y = (short) (x % y); x = temp; } return x; }
	public static float gcd(float x, float y) { while(y != 0) { float temp = y; y = x % y; x = temp; } return x; }
	public static double gcd(double x, double y) { while(y != 0) { double temp = y; y = x % y; x = temp; } return x; }
	public static char gcd(char x, char y) { while(y != 0) { char temp = y; y = (char) (x % y); x = temp; } return x; }
	public static int lcm(int x, int y) { return (x * y) / gcd(x, y); }
	public static long lcm(long x, long y) { return (x * y) / gcd(x, y); }
	public static short lcm(short x, short y) { return (short) ((x * y) / gcd(x, y)); }
	public static float lcm(float x, float y) { return (x * y) / gcd(x, y); }
	public static double lcm(double x, double y) { return (x * y) / gcd(x, y); }
	public static char lcm(char x, char y) { return (char) ((x * y) / gcd(x, y)); }

	public static int add(int[] x) { int result = 0; for(int _x : x) result += _x; return result; }
	public static long add(long[] x) { long result = 0; for(long _x : x) result += _x; return result; }
	public static short add(short[] x) { short result = 0; for(short _x : x) result += _x; return result; }
	public static float add(float[] x) { float result = 0; for(float _x : x) result += _x; return result; }
	public static double add(double[] x) { double result = 0; for(double _x : x) result += _x; return result; }
	public static char add(char[] x) { char result = 0; for(char _x : x) result += _x; return result; }
	public static int sub(int[] x) { int result = 0; for(int _x : x) result -= _x; return result; }
	public static long sub(long[] x) { long result = 0; for(long _x : x) result -= _x; return result; }
	public static short sub(short[] x) { short result = 0; for(short _x : x) result -= _x; return result; }
	public static float sub(float[] x) { float result = 0; for(float _x : x) result -= _x; return result; }
	public static double sub(double[] x) { double result = 0; for(double _x : x) result -= _x; return result; }
	public static char sub(char[] x) { char result = 0; for(char _x : x) result -= _x; return result; }
	public static int mul(int[] x) { int result = 1; for(int _x : x) result *= _x; return result; }
	public static long mul(long[] x) { long result = 1; for(long _x : x) result *= _x; return result; }
	public static short mul(short[] x) { short result = 1; for(short _x : x) result *= _x; return result; }
	public static float mul(float[] x) { float result = 1; for(float _x : x) result *= _x; return result; }
	public static double mul(double[] x) { double result = 1; for(double _x : x) result *= _x; return result; }
	public static char mul(char[] x) { char result = 1; for(char _x : x) result *= _x; return result; }
	public static int div(int[] x) { int result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result /= x[i]; return result; }
	public static long div(long[] x) { long result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result /= x[i]; return result; }
	public static short div(short[] x) { short result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result /= x[i]; return result; }
	public static float div(float[] x) { float result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result /= x[i]; return result; }
	public static double div(double[] x) { double result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result /= x[i]; return result; }
	public static char div(char[] x) { char result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result /= x[i]; return result; }
	public static int mod(int[] x) { int result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result %= x[i]; return result; }
	public static long mod(long[] x) { long result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result %= x[i]; return result; }
	public static short mod(short[] x) { short result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result %= x[i]; return result; }
	public static float mod(float[] x) { float result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result %= x[i]; return result; }
	public static double mod(double[] x) { double result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result %= x[i]; return result; }
	public static char mod(char[] x) { char result = x.length > 0 ? x[0] : 0; for(int i = 1; i < x.length; i++) result %= x[i]; return result; }

	public static int[] add(int[] x, int a, int[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] + a; target[i + 1] = x[i + 1] + a; target[i + 2] = x[i + 2] + a; target[i + 3] = x[i + 3] + a; } for(; i < target.length; i++) target[i] = x[i] + a; return target; }
	public static long[] add(long[] x, long a, long[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] + a; target[i + 1] = x[i + 1] + a; target[i + 2] = x[i + 2] + a; target[i + 3] = x[i + 3] + a; } for(; i < target.length; i++) target[i] = x[i] + a; return target; }
	public static short[] add(short[] x, short a, short[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = (short) (x[i] + a); target[i + 1] = (short) (x[i + 1] + a); target[i + 2] = (short) (x[i + 2] + a); target[i + 3] = (short) (x[i + 3] + a); } for(; i < target.length; i++) target[i] = (short) (x[i] + a); return target; }
	public static float[] add(float[] x, float a, float[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] + a; target[i + 1] = x[i + 1] + a; target[i + 2] = x[i + 2] + a; target[i + 3] = x[i + 3] + a; } for(; i < target.length; i++) target[i] = x[i] + a; return target; }
	public static double[] add(double[] x, double a, double[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] + a; target[i + 1] = x[i + 1] + a; target[i + 2] = x[i + 2] + a; target[i + 3] = x[i + 3] + a; } for(; i < target.length; i++) target[i] = x[i] + a; return target; }
	public static char[] add(char[] x, char a, char[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = (char) (x[i] + a); target[i + 1] = (char) (x[i + 1] + a); target[i + 2] = (char) (x[i + 2] + a); target[i + 3] = (char) (x[i + 3] + a); } for(; i < target.length; i++) target[i] = (char) (x[i] + a); return target; }
	public static int[] sub(int[] x, int a, int[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] - a; target[i + 1] = x[i + 1] - a; target[i + 2] = x[i + 2] - a; target[i + 3] = x[i + 3] - a; } for(; i < target.length; i++) target[i] = x[i] - a; return target; }
	public static long[] sub(long[] x, long a, long[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] - a; target[i + 1] = x[i + 1] - a; target[i + 2] = x[i + 2] - a; target[i + 3] = x[i + 3] - a; } for(; i < target.length; i++) target[i] = x[i] - a; return target; }
	public static short[] sub(short[] x, short a, short[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = (short) (x[i] - a); target[i + 1] = (short) (x[i + 1] - a); target[i + 2] = (short) (x[i + 2] - a); target[i + 3] = (short) (x[i + 3] - a); } for(; i < target.length; i++) target[i] = (short) (x[i] - a); return target; }
	public static float[] sub(float[] x, float a, float[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] - a; target[i + 1] = x[i + 1] - a; target[i + 2] = x[i + 2] - a; target[i + 3] = x[i + 3] - a; } for(; i < target.length; i++) target[i] = x[i] - a; return target; }
	public static double[] sub(double[] x, double a, double[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] - a; target[i + 1] = x[i + 1] - a; target[i + 2] = x[i + 2] - a; target[i + 3] = x[i + 3] - a; } for(; i < target.length; i++) target[i] = x[i] - a; return target; }
	public static char[] sub(char[] x, char a, char[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = (char) (x[i] - a); target[i + 1] = (char) (x[i + 1] - a); target[i + 2] = (char) (x[i + 2] - a); target[i + 3] = (char) (x[i + 3] - a); } for(; i < target.length; i++) target[i] = (char) (x[i] - a); return target; }
	public static int[] mul(int[] x, int a, int[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] * a; target[i + 1] = x[i + 1] * a; target[i + 2] = x[i + 2] * a; target[i + 3] = x[i + 3] * a; } for(; i < target.length; i++) target[i] = x[i] * a; return target; }
	public static long[] mul(long[] x, long a, long[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] * a; target[i + 1] = x[i + 1] * a; target[i + 2] = x[i + 2] * a; target[i + 3] = x[i + 3] * a; } for(; i < target.length; i++) target[i] = x[i] * a; return target; }
	public static short[] mul(short[] x, short a, short[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = (short) (x[i] * a); target[i + 1] = (short) (x[i + 1] * a); target[i + 2] = (short) (x[i + 2] * a); target[i + 3] = (short) (x[i + 3] * a); } for(; i < target.length; i++) target[i] = (short) (x[i] * a); return target; }
	public static float[] mul(float[] x, float a, float[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] * a; target[i + 1] = x[i + 1] * a; target[i + 2] = x[i + 2] * a; target[i + 3] = x[i + 3] * a; } for(; i < target.length; i++) target[i] = x[i] * a; return target; }
	public static double[] mul(double[] x, double a, double[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] * a; target[i + 1] = x[i + 1] * a; target[i + 2] = x[i + 2] * a; target[i + 3] = x[i + 3] * a; } for(; i < target.length; i++) target[i] = x[i] * a; return target; }
	public static char[] mul(char[] x, char a, char[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = (char) (x[i] * a); target[i + 1] = (char) (x[i + 1] * a); target[i + 2] = (char) (x[i + 2] * a); target[i + 3] = (char) (x[i + 3] * a); } for(; i < target.length; i++) target[i] = (char) (x[i] * a); return target; }
	public static int[] div(int[] x, int a, int[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] / a; target[i + 1] = x[i + 1] / a; target[i + 2] = x[i + 2] / a; target[i + 3] = x[i + 3] / a; } for(; i < target.length; i++) target[i] = x[i] / a; return target; }
	public static long[] div(long[] x, long a, long[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] / a; target[i + 1] = x[i + 1] / a; target[i + 2] = x[i + 2] / a; target[i + 3] = x[i + 3] / a; } for(; i < target.length; i++) target[i] = x[i] / a; return target; }
	public static short[] div(short[] x, short a, short[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = (short) (x[i] / a); target[i + 1] = (short) (x[i + 1] / a); target[i + 2] = (short) (x[i + 2] / a); target[i + 3] = (short) (x[i + 3] / a); } for(; i < target.length; i++) target[i] = (short) (x[i] / a); return target; }
	public static float[] div(float[] x, float a, float[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] / a; target[i + 1] = x[i + 1] / a; target[i + 2] = x[i + 2] / a; target[i + 3] = x[i + 3] / a; } for(; i < target.length; i++) target[i] = x[i] / a; return target; }
	public static double[] div(double[] x, double a, double[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] / a; target[i + 1] = x[i + 1] / a; target[i + 2] = x[i + 2] / a; target[i + 3] = x[i + 3] / a; } for(; i < target.length; i++) target[i] = x[i] / a; return target; }
	public static char[] div(char[] x, char a, char[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = (char) (x[i] / a); target[i + 1] = (char) (x[i + 1] / a); target[i + 2] = (char) (x[i + 2] / a); target[i + 3] = (char) (x[i + 3] / a); } for(; i < target.length; i++) target[i] = (char) (x[i] / a); return target; }
	public static int[] mod(int[] x, int a, int[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] % a; target[i + 1] = x[i + 1] % a; target[i + 2] = x[i + 2] % a; target[i + 3] = x[i + 3] % a; } for(; i < target.length; i++) target[i] = x[i] % a; return target; }
	public static long[] mod(long[] x, long a, long[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] % a; target[i + 1] = x[i + 1] % a; target[i + 2] = x[i + 2] % a; target[i + 3] = x[i + 3] % a; } for(; i < target.length; i++) target[i] = x[i] % a; return target; }
	public static short[] mod(short[] x, short a, short[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = (short) (x[i] % a); target[i + 1] = (short) (x[i + 1] % a); target[i + 2] = (short) (x[i + 2] % a); target[i + 3] = (short) (x[i + 3] % a); } for(; i < target.length; i++) target[i] = (short) (x[i] % a); return target; }
	public static float[] mod(float[] x, float a, float[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] % a; target[i + 1] = x[i + 1] % a; target[i + 2] = x[i + 2] % a; target[i + 3] = x[i + 3] % a; } for(; i < target.length; i++) target[i] = x[i] % a; return target; }
	public static double[] mod(double[] x, double a, double[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = x[i] % a; target[i + 1] = x[i + 1] % a; target[i + 2] = x[i + 2] % a; target[i + 3] = x[i + 3] % a; } for(; i < target.length; i++) target[i] = x[i] % a; return target; }
	public static char[] mod(char[] x, char a, char[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = (char) (x[i] % a); target[i + 1] = (char) (x[i + 1] % a); target[i + 2] = (char) (x[i + 2] % a); target[i + 3] = (char) (x[i + 3] % a); } for(; i < target.length; i++) target[i] = (char) (x[i] % a); return target; }

	public static String romanNumerals(int romanNum) {
		StringBuilder result = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		try { for(Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
			int matches = romanNum / entry.getValue();
			result.append(StringUtils.repeat(entry.getKey(), matches));
			romanNum %= entry.getValue();
		} return result.toString(); } finally { Pool.returnObject(StringBuilder.class, result); }
	}

	public interface FastMathImplementation {
		public int[] rand(int min, int max, Random random, int[] target, int targetOff, int len);
		public long[] rand(long min, long max, Random random, long[] target, int targetOff, int len);
		public short[] rand(short min, short max, Random random, short[] target, int targetOff, int len);
		public float[] rand(float min, float max, Random random, float[] target, int targetOff, int len);
		public double[] rand(double min, double max, Random random, double[] target, int targetOff, int len);
		public char[] rand(char min, char max, Random random, char[] target, int targetOff, int len);
		public int[] rand(int min, int max, Random random, int[] target, int targetOff);
		public long[] rand(long min, long max, Random random, long[] target, int targetOff);
		public short[] rand(short min, short max, Random random, short[] target, int targetOff);
		public float[] rand(float min, float max, Random random, float[] target, int targetOff);
		public double[] rand(double min, double max, Random random, double[] target, int targetOff);
		public char[] rand(char min, char max, Random random, char[] target, int targetOff);
		public int[] rand(int min, int max, Random random, int[] target);
		public long[] rand(long min, long max, Random random, long[] target);
		public short[] rand(short min, short max, Random random, short[] target);
		public float[] rand(float min, float max, Random random, float[] target);
		public double[] rand(double min, double max, Random random, double[] target);
		public char[] rand(char min, char max, Random random, char[] target);
		public int[] rand(int min, int max, int[] target);
		public long[] rand(long min, long max, long[] target);
		public short[] rand(short min, short max, short[] target);
		public float[] rand(float min, float max, float[] target);
		public double[] rand(double min, double max, double[] target);
		public char[] rand(char min, char max, char[] target);
		public float[] fma(float[] a, int aOff, float[] b, int bOff, float[] c, int cOff, float[] target, int targetOff, int len);
		public double[] fma(double[] a, int aOff, double[] b, int bOff, double[] c, int cOff, double[] target, int targetOff, int len);
		public float[] fma(float[] a, int aOff, float[] b, int bOff, float[] c, int cOff, float[] target, int targetOff);
		public double[] fma(double[] a, int aOff, double[] b, int bOff, double[] c, int cOff, double[] target, int targetOff);
		public float[] fma(float[] a, float[] b, float[] c, float[] target);
		public double[] fma(double[] a, double[] b, double[] c, double[] target);
		public float[] _fma(float[] a, float[] b, float[] c);
		public double[] _fma(double[] a, double[] b, double[] c);
		public float[] fma(float[] a, int aOff, float[] b, int bOff, float[] target, int targetOff, int len);
		public double[] fma(double[] a, int aOff, double[] b, int bOff, double[] target, int targetOff, int len);
		public float[] fma(float[] a, int aOff, float[] b, int bOff, float[] target, int targetOff);
		public double[] fma(double[] a, int aOff, double[] b, int bOff, double[] target, int targetOff);
		public float[] fma(float[] a, float[] b, float[] target);
		public double[] fma(double[] a, double[] b, double[] target);
		public float[] _fma(float[] a, float[] b);
		public double[] _fma(double[] a, double[] b);
		public double[] radians(int[] degrees, int degreesOff, double[] target, int targetOff, int len);
		public double[] radians(long[] degrees, int degreesOff, double[] target, int targetOff, int len);
		public double[] radians(short[] degrees, int degreesOff, double[] target, int targetOff, int len);
		public double[] radians(float[] degrees, int degreesOff, double[] target, int targetOff, int len);
		public double[] radians(double[] degrees, int degreesOff, double[] target, int targetOff, int len);
		public double[] radians(char[] degrees, int degreesOff, double[] target, int targetOff, int len);
		public double[] radians(int[] degrees, int degreesOff, double[] target, int targetOff);
		public double[] radians(long[] degrees, int degreesOff, double[] target, int targetOff);
		public double[] radians(short[] degrees, int degreesOff, double[] target, int targetOff);
		public double[] radians(float[] degrees, int degreesOff, double[] target, int targetOff);
		public double[] radians(double[] degrees, int degreesOff, double[] target, int targetOff);
		public double[] radians(char[] degrees, int degreesOff, double[] target, int targetOff);
		public double[] radians(int[] degrees, double[] target);
		public double[] radians(long[] degrees, double[] target);
		public double[] radians(short[] degrees, double[] target);
		public double[] radians(float[] degrees, double[] target);
		public double[] radians(double[] degrees, double[] target);
		public double[] radians(char[] degrees, double[] target);
		public double[] radians(int[] degrees);
		public double[] radians(long[] degrees);
		public double[] radians(short[] degrees);
		public double[] radians(float[] degrees);
		public double[] radians(double[] degrees);
		public double[] radians(char[] degrees);
		public double[] degrees(int[] radians, int radiansOff, double[] target, int targetOff, int len);
		public double[] degrees(long[] radians, int radiansOff, double[] target, int targetOff, int len);
		public double[] degrees(short[] radians, int radiansOff, double[] target, int targetOff, int len);
		public double[] degrees(float[] radians, int radiansOff, double[] target, int targetOff, int len);
		public double[] degrees(double[] radians, int radiansOff, double[] target, int targetOff, int len);
		public double[] degrees(char[] radians, int radiansOff, double[] target, int targetOff, int len);
		public double[] degrees(int[] radians, int radiansOff, double[] target, int targetOff);
		public double[] degrees(long[] radians, int radiansOff, double[] target, int targetOff);
		public double[] degrees(short[] radians, int radiansOff, double[] target, int targetOff);
		public double[] degrees(float[] radians, int radiansOff, double[] target, int targetOff);
		public double[] degrees(double[] radians, int radiansOff, double[] target, int targetOff);
		public double[] degrees(char[] radians, int radiansOff, double[] target, int targetOff);
		public double[] degrees(int[] radians, double[] target);
		public double[] degrees(long[] radians, double[] target);
		public double[] degrees(short[] radians, double[] target);
		public double[] degrees(float[] radians, double[] target);
		public double[] degrees(double[] radians, double[] target);
		public double[] degrees(char[] radians, double[] target);
		public double[] degrees(int[] radians);
		public double[] degrees(long[] radians);
		public double[] degrees(short[] radians);
		public double[] degrees(float[] radians);
		public double[] degrees(double[] radians);
		public double[] degrees(char[] radians);
		public double[] sin(int[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] sin(long[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] sin(short[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] sin(float[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] sin(double[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] sin(char[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] sin(int[] angles, int anglesOff, double[] target, int targetOff);
		public double[] sin(long[] angles, int anglesOff, double[] target, int targetOff);
		public double[] sin(short[] angles, int anglesOff, double[] target, int targetOff);
		public double[] sin(float[] angles, int anglesOff, double[] target, int targetOff);
		public double[] sin(double[] angles, int anglesOff, double[] target, int targetOff);
		public double[] sin(char[] angles, int anglesOff, double[] target, int targetOff);
		public double[] sin(int[] angles, double[] target);
		public double[] sin(long[] angles, double[] target);
		public double[] sin(short[] angles, double[] target);
		public double[] sin(float[] angles, double[] target);
		public double[] sin(double[] angles, double[] target);
		public double[] sin(char[] angles, double[] target);
		public double[] sin(int[] angles);
		public double[] sin(long[] angles);
		public double[] sin(short[] angles);
		public double[] sin(float[] angles);
		public double[] sin(double[] angles);
		public double[] sin(char[] angles);
		public double[] cos(int[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] cos(long[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] cos(short[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] cos(float[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] cos(double[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] cos(char[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] cos(int[] angles, int anglesOff, double[] target, int targetOff);
		public double[] cos(long[] angles, int anglesOff, double[] target, int targetOff);
		public double[] cos(short[] angles, int anglesOff, double[] target, int targetOff);
		public double[] cos(float[] angles, int anglesOff, double[] target, int targetOff);
		public double[] cos(double[] angles, int anglesOff, double[] target, int targetOff);
		public double[] cos(char[] angles, int anglesOff, double[] target, int targetOff);
		public double[] cos(int[] angles, double[] target);
		public double[] cos(long[] angles, double[] target);
		public double[] cos(short[] angles, double[] target);
		public double[] cos(float[] angles, double[] target);
		public double[] cos(double[] angles, double[] target);
		public double[] cos(char[] angles, double[] target);
		public double[] cos(int[] angles);
		public double[] cos(long[] angles);
		public double[] cos(short[] angles);
		public double[] cos(float[] angles);
		public double[] cos(double[] angles);
		public double[] cos(char[] angles);
		public double[] tan(int[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] tan(long[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] tan(short[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] tan(float[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] tan(double[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] tan(char[] angles, int anglesOff, double[] target, int targetOff, int len);
		public double[] tan(int[] angles, int anglesOff, double[] target, int targetOff);
		public double[] tan(long[] angles, int anglesOff, double[] target, int targetOff);
		public double[] tan(short[] angles, int anglesOff, double[] target, int targetOff);
		public double[] tan(float[] angles, int anglesOff, double[] target, int targetOff);
		public double[] tan(double[] angles, int anglesOff, double[] target, int targetOff);
		public double[] tan(char[] angles, int anglesOff, double[] target, int targetOff);
		public double[] tan(int[] angles, double[] target);
		public double[] tan(long[] angles, double[] target);
		public double[] tan(short[] angles, double[] target);
		public double[] tan(float[] angles, double[] target);
		public double[] tan(double[] angles, double[] target);
		public double[] tan(char[] angles, double[] target);
		public double[] tan(int[] angles);
		public double[] tan(long[] angles);
		public double[] tan(short[] angles);
		public double[] tan(float[] angles);
		public double[] tan(double[] angles);
		public double[] tan(char[] angles);
		public double[] asin(int[] y, int yOff, double[] target, int targetOff, int len);
		public double[] asin(long[] y, int yOff, double[] target, int targetOff, int len);
		public double[] asin(short[] y, int yOff, double[] target, int targetOff, int len);
		public double[] asin(float[] y, int yOff, double[] target, int targetOff, int len);
		public double[] asin(double[] y, int yOff, double[] target, int targetOff, int len);
		public double[] asin(char[] y, int yOff, double[] target, int targetOff, int len);
		public double[] asin(int[] y, int yOff, double[] target, int targetOff);
		public double[] asin(long[] y, int yOff, double[] target, int targetOff);
		public double[] asin(short[] y, int yOff, double[] target, int targetOff);
		public double[] asin(float[] y, int yOff, double[] target, int targetOff);
		public double[] asin(double[] y, int yOff, double[] target, int targetOff);
		public double[] asin(char[] y, int yOff, double[] target, int targetOff);
		public double[] asin(int[] y, double[] target);
		public double[] asin(long[] y, double[] target);
		public double[] asin(short[] y, double[] target);
		public double[] asin(float[] y, double[] target);
		public double[] asin(double[] y, double[] target);
		public double[] asin(char[] y, double[] target);
		public double[] asin(int[] y);
		public double[] asin(long[] y);
		public double[] asin(short[] y);
		public double[] asin(float[] y);
		public double[] asin(double[] y);
		public double[] asin(char[] y);
		public double[] acos(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] acos(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] acos(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] acos(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] acos(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] acos(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] acos(int[] x, int xOff, double[] target, int targetOff);
		public double[] acos(long[] x, int xOff, double[] target, int targetOff);
		public double[] acos(short[] x, int xOff, double[] target, int targetOff);
		public double[] acos(float[] x, int xOff, double[] target, int targetOff);
		public double[] acos(double[] x, int xOff, double[] target, int targetOff);
		public double[] acos(char[] x, int xOff, double[] target, int targetOff);
		public double[] acos(int[] x, double[] target);
		public double[] acos(long[] x, double[] target);
		public double[] acos(short[] x, double[] target);
		public double[] acos(float[] x, double[] target);
		public double[] acos(double[] x, double[] target);
		public double[] acos(char[] x, double[] target);
		public double[] acos(int[] x);
		public double[] acos(long[] x);
		public double[] acos(short[] x);
		public double[] acos(float[] x);
		public double[] acos(double[] x);
		public double[] acos(char[] x);
		public double[] atan(int[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len);
		public double[] atan(long[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len);
		public double[] atan(short[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len);
		public double[] atan(float[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len);
		public double[] atan(double[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len);
		public double[] atan(char[] y_over_x, int y_over_xOff, double[] target, int targetOff, int len);
		public double[] atan(int[] y_over_x, int y_over_xOff, double[] target, int targetOff);
		public double[] atan(long[] y_over_x, int y_over_xOff, double[] target, int targetOff);
		public double[] atan(short[] y_over_x, int y_over_xOff, double[] target, int targetOff);
		public double[] atan(float[] y_over_x, int y_over_xOff, double[] target, int targetOff);
		public double[] atan(double[] y_over_x, int y_over_xOff, double[] target, int targetOff);
		public double[] atan(char[] y_over_x, int y_over_xOff, double[] target, int targetOff);
		public double[] atan(int[] y_over_x, double[] target);
		public double[] atan(long[] y_over_x, double[] target);
		public double[] atan(short[] y_over_x, double[] target);
		public double[] atan(float[] y_over_x, double[] target);
		public double[] atan(double[] y_over_x, double[] target);
		public double[] atan(char[] y_over_x, double[] target);
		public double[] atan(int[] y_over_x);
		public double[] atan(long[] y_over_x);
		public double[] atan(short[] y_over_x);
		public double[] atan(float[] y_over_x);
		public double[] atan(double[] y_over_x);
		public double[] atan(char[] y_over_x);
		public double[] atanYX(int[] y, int yOff, int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atanYX(long[] y, int yOff, long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atanYX(short[] y, int yOff, short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atanYX(float[] y, int yOff, float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atanYX(double[] y, int yOff, double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atanYX(char[] y, int yOff, char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atanYX(int[] y, int yOff, int[] x, int xOff, double[] target, int targetOff);
		public double[] atanYX(long[] y, int yOff, long[] x, int xOff, double[] target, int targetOff);
		public double[] atanYX(short[] y, int yOff, short[] x, int xOff, double[] target, int targetOff);
		public double[] atanYX(float[] y, int yOff, float[] x, int xOff, double[] target, int targetOff);
		public double[] atanYX(double[] y, int yOff, double[] x, int xOff, double[] target, int targetOff);
		public double[] atanYX(char[] y, int yOff, char[] x, int xOff, double[] target, int targetOff);
		public double[] atanYX(int[] y, int[] x, double[] target);
		public double[] atanYX(long[] y, long[] x, double[] target);
		public double[] atanYX(short[] y, short[] x, double[] target);
		public double[] atanYX(float[] y, float[] x, double[] target);
		public double[] atanYX(double[] y, double[] x, double[] target);
		public double[] atanYX(char[] y, char[] x, double[] target);
		public double[] atanYX(int[] y, int[] x);
		public double[] atanYX(long[] y, long[] x);
		public double[] atanYX(short[] y, short[] x);
		public double[] atanYX(float[] y, float[] x);
		public double[] atanYX(double[] y, double[] x);
		public double[] atanYX(char[] y, char[] x);
		public double[] atan2(int[] y, int yOff, int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atan2(long[] y, int yOff, long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atan2(short[] y, int yOff, short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atan2(float[] y, int yOff, float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atan2(double[] y, int yOff, double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atan2(char[] y, int yOff, char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] atan2(int[] y, int yOff, int[] x, int xOff, double[] target, int targetOff);
		public double[] atan2(long[] y, int yOff, long[] x, int xOff, double[] target, int targetOff);
		public double[] atan2(short[] y, int yOff, short[] x, int xOff, double[] target, int targetOff);
		public double[] atan2(float[] y, int yOff, float[] x, int xOff, double[] target, int targetOff);
		public double[] atan2(double[] y, int yOff, double[] x, int xOff, double[] target, int targetOff);
		public double[] atan2(char[] y, int yOff, char[] x, int xOff, double[] target, int targetOff);
		public double[] atan2(int[] y, int[] x, double[] target);
		public double[] atan2(long[] y, long[] x, double[] target);
		public double[] atan2(short[] y, short[] x, double[] target);
		public double[] atan2(float[] y, float[] x, double[] target);
		public double[] atan2(double[] y, double[] x, double[] target);
		public double[] atan2(char[] y, char[] x, double[] target);
		public double[] atan2(int[] y, int[] x);
		public double[] atan2(long[] y, long[] x);
		public double[] atan2(short[] y, short[] x);
		public double[] atan2(float[] y, float[] x);
		public double[] atan2(double[] y, double[] x);
		public double[] atan2(char[] y, char[] x);
		public int[] pow(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff, int len);
		public long[] pow(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff, int len);
		public short[] pow(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff, int len);
		public float[] pow(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff, int len);
		public double[] pow(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len);
		public char[] pow(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff, int len);
		public int[] pow(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff);
		public long[] pow(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff);
		public short[] pow(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff);
		public float[] pow(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff);
		public double[] pow(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff);
		public char[] pow(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff);
		public int[] pow(int[] x, int[] y, int[] target);
		public long[] pow(long[] x, long[] y, long[] target);
		public short[] pow(short[] x, short[] y, short[] target);
		public float[] pow(float[] x, float[] y, float[] target);
		public double[] pow(double[] x, double[] y, double[] target);
		public char[] pow(char[] x, char[] y, char[] target);
		public int[] pow(int[] x, int[] y);
		public long[] pow(long[] x, long[] y);
		public short[] pow(short[] x, short[] y);
		public float[] pow(float[] x, float[] y);
		public double[] pow(double[] x, double[] y);
		public char[] pow(char[] x, char[] y);
		public double[] powD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff, int len);
		public double[] powD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff, int len);
		public double[] powD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff, int len);
		public double[] powD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff, int len);
		public double[] powD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len);
		public double[] powD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff, int len);
		public double[] powD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff);
		public double[] powD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff);
		public double[] powD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff);
		public double[] powD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff);
		public double[] powD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff);
		public double[] powD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff);
		public double[] powD(int[] x, int[] y, double[] target);
		public double[] powD(long[] x, long[] y, double[] target);
		public double[] powD(short[] x, short[] y, double[] target);
		public double[] powD(float[] x, float[] y, double[] target);
		public double[] powD(double[] x, double[] y, double[] target);
		public double[] powD(char[] x, char[] y, double[] target);
		public double[] powD(int[] x, int[] y);
		public double[] powD(long[] x, long[] y);
		public double[] powD(short[] x, short[] y);
		public double[] powD(float[] x, float[] y);
		public double[] powD(double[] x, double[] y);
		public double[] powD(char[] x, char[] y);
		public double[] exp(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp(int[] x, int xOff, double[] target, int targetOff);
		public double[] exp(long[] x, int xOff, double[] target, int targetOff);
		public double[] exp(short[] x, int xOff, double[] target, int targetOff);
		public double[] exp(float[] x, int xOff, double[] target, int targetOff);
		public double[] exp(double[] x, int xOff, double[] target, int targetOff);
		public double[] exp(char[] x, int xOff, double[] target, int targetOff);
		public double[] exp(int[] x, double[] target);
		public double[] exp(long[] x, double[] target);
		public double[] exp(short[] x, double[] target);
		public double[] exp(float[] x, double[] target);
		public double[] exp(double[] x, double[] target);
		public double[] exp(char[] x, double[] target);
		public double[] exp(int[] x);
		public double[] exp(long[] x);
		public double[] exp(short[] x);
		public double[] exp(float[] x);
		public double[] exp(double[] x);
		public double[] exp(char[] x);
		public double[] log(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log(int[] x, int xOff, double[] target, int targetOff);
		public double[] log(long[] x, int xOff, double[] target, int targetOff);
		public double[] log(short[] x, int xOff, double[] target, int targetOff);
		public double[] log(float[] x, int xOff, double[] target, int targetOff);
		public double[] log(double[] x, int xOff, double[] target, int targetOff);
		public double[] log(char[] x, int xOff, double[] target, int targetOff);
		public double[] log(int[] x, double[] target);
		public double[] log(long[] x, double[] target);
		public double[] log(short[] x, double[] target);
		public double[] log(float[] x, double[] target);
		public double[] log(double[] x, double[] target);
		public double[] log(char[] x, double[] target);
		public double[] log(int[] x);
		public double[] log(long[] x);
		public double[] log(short[] x);
		public double[] log(float[] x);
		public double[] log(double[] x);
		public double[] log(char[] x);
		public double[] exp2(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp2(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp2(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp2(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp2(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp2(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] exp2(int[] x, int xOff, double[] target, int targetOff);
		public double[] exp2(long[] x, int xOff, double[] target, int targetOff);
		public double[] exp2(short[] x, int xOff, double[] target, int targetOff);
		public double[] exp2(float[] x, int xOff, double[] target, int targetOff);
		public double[] exp2(double[] x, int xOff, double[] target, int targetOff);
		public double[] exp2(char[] x, int xOff, double[] target, int targetOff);
		public double[] exp2(int[] x, double[] target);
		public double[] exp2(long[] x, double[] target);
		public double[] exp2(short[] x, double[] target);
		public double[] exp2(float[] x, double[] target);
		public double[] exp2(double[] x, double[] target);
		public double[] exp2(char[] x, double[] target);
		public double[] exp2(int[] x);
		public double[] exp2(long[] x);
		public double[] exp2(short[] x);
		public double[] exp2(float[] x);
		public double[] exp2(double[] x);
		public double[] exp2(char[] x);
		public double[] log2(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log2(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log2(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log2(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log2(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log2(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] log2(int[] x, int xOff, double[] target, int targetOff);
		public double[] log2(long[] x, int xOff, double[] target, int targetOff);
		public double[] log2(short[] x, int xOff, double[] target, int targetOff);
		public double[] log2(float[] x, int xOff, double[] target, int targetOff);
		public double[] log2(double[] x, int xOff, double[] target, int targetOff);
		public double[] log2(char[] x, int xOff, double[] target, int targetOff);
		public double[] log2(int[] x, double[] target);
		public double[] log2(long[] x, double[] target);
		public double[] log2(short[] x, double[] target);
		public double[] log2(float[] x, double[] target);
		public double[] log2(double[] x, double[] target);
		public double[] log2(char[] x, double[] target);
		public double[] log2(int[] x);
		public double[] log2(long[] x);
		public double[] log2(short[] x);
		public double[] log2(float[] x);
		public double[] log2(double[] x);
		public double[] log2(char[] x);
		public double[] sqrt(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] sqrt(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] sqrt(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] sqrt(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] sqrt(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] sqrt(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] sqrt(int[] x, int xOff, double[] target, int targetOff);
		public double[] sqrt(long[] x, int xOff, double[] target, int targetOff);
		public double[] sqrt(short[] x, int xOff, double[] target, int targetOff);
		public double[] sqrt(float[] x, int xOff, double[] target, int targetOff);
		public double[] sqrt(double[] x, int xOff, double[] target, int targetOff);
		public double[] sqrt(char[] x, int xOff, double[] target, int targetOff);
		public double[] sqrt(int[] x, double[] target);
		public double[] sqrt(long[] x, double[] target);
		public double[] sqrt(short[] x, double[] target);
		public double[] sqrt(float[] x, double[] target);
		public double[] sqrt(double[] x, double[] target);
		public double[] sqrt(char[] x, double[] target);
		public double[] sqrt(int[] x);
		public double[] sqrt(long[] x);
		public double[] sqrt(short[] x);
		public double[] sqrt(float[] x);
		public double[] sqrt(double[] x);
		public double[] sqrt(char[] x);
		public double[] inversesqrt(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] inversesqrt(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] inversesqrt(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] inversesqrt(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] inversesqrt(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] inversesqrt(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] inversesqrt(int[] x, int xOff, double[] target, int targetOff);
		public double[] inversesqrt(long[] x, int xOff, double[] target, int targetOff);
		public double[] inversesqrt(short[] x, int xOff, double[] target, int targetOff);
		public double[] inversesqrt(float[] x, int xOff, double[] target, int targetOff);
		public double[] inversesqrt(double[] x, int xOff, double[] target, int targetOff);
		public double[] inversesqrt(char[] x, int xOff, double[] target, int targetOff);
		public double[] inversesqrt(int[] x, double[] target);
		public double[] inversesqrt(long[] x, double[] target);
		public double[] inversesqrt(short[] x, double[] target);
		public double[] inversesqrt(float[] x, double[] target);
		public double[] inversesqrt(double[] x, double[] target);
		public double[] inversesqrt(char[] x, double[] target);
		public double[] inversesqrt(int[] x);
		public double[] inversesqrt(long[] x);
		public double[] inversesqrt(short[] x);
		public double[] inversesqrt(float[] x);
		public double[] inversesqrt(double[] x);
		public double[] inversesqrt(char[] x);
		public int[] abs(int[] x, int xOff, int[] target, int targetOff, int len);
		public long[] abs(long[] x, int xOff, long[] target, int targetOff, int len);
		public short[] abs(short[] x, int xOff, short[] target, int targetOff, int len);
		public float[] abs(float[] x, int xOff, float[] target, int targetOff, int len);
		public double[] abs(double[] x, int xOff, double[] target, int targetOff, int len);
		public char[] abs(char[] x, int xOff, char[] target, int targetOff, int len);
		public int[] abs(int[] x, int xOff, int[] target, int targetOff);
		public long[] abs(long[] x, int xOff, long[] target, int targetOff);
		public short[] abs(short[] x, int xOff, short[] target, int targetOff);
		public float[] abs(float[] x, int xOff, float[] target, int targetOff);
		public double[] abs(double[] x, int xOff, double[] target, int targetOff);
		public char[] abs(char[] x, int xOff, char[] target, int targetOff);
		public int[] abs(int[] x, int[] target);
		public long[] abs(long[] x, long[] target);
		public short[] abs(short[] x, short[] target);
		public float[] abs(float[] x, float[] target);
		public double[] abs(double[] x, double[] target);
		public char[] abs(char[] x, char[] target);
		public int[] abs(int[] x);
		public long[] abs(long[] x);
		public short[] abs(short[] x);
		public float[] abs(float[] x);
		public double[] abs(double[] x);
		public char[] abs(char[] x);
		public double[] absD(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] absD(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] absD(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] absD(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] absD(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] absD(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] absD(int[] x, int xOff, double[] target, int targetOff);
		public double[] absD(long[] x, int xOff, double[] target, int targetOff);
		public double[] absD(short[] x, int xOff, double[] target, int targetOff);
		public double[] absD(float[] x, int xOff, double[] target, int targetOff);
		public double[] absD(double[] x, int xOff, double[] target, int targetOff);
		public double[] absD(char[] x, int xOff, double[] target, int targetOff);
		public double[] absD(int[] x, double[] target);
		public double[] absD(long[] x, double[] target);
		public double[] absD(short[] x, double[] target);
		public double[] absD(float[] x, double[] target);
		public double[] absD(double[] x, double[] target);
		public double[] absD(char[] x, double[] target);
		public double[] absD(int[] x);
		public double[] absD(long[] x);
		public double[] absD(short[] x);
		public double[] absD(float[] x);
		public double[] absD(double[] x);
		public double[] absD(char[] x);
		public int[] sign(int[] x, int xOff, int[] target, int targetOff, int len);
		public long[] sign(long[] x, int xOff, long[] target, int targetOff, int len);
		public short[] sign(short[] x, int xOff, short[] target, int targetOff, int len);
		public float[] sign(float[] x, int xOff, float[] target, int targetOff, int len);
		public double[] sign(double[] x, int xOff, double[] target, int targetOff, int len);
		public char[] sign(char[] x, int xOff, char[] target, int targetOff, int len);
		public int[] sign(int[] x, int xOff, int[] target, int targetOff);
		public long[] sign(long[] x, int xOff, long[] target, int targetOff);
		public short[] sign(short[] x, int xOff, short[] target, int targetOff);
		public float[] sign(float[] x, int xOff, float[] target, int targetOff);
		public double[] sign(double[] x, int xOff, double[] target, int targetOff);
		public char[] sign(char[] x, int xOff, char[] target, int targetOff);
		public int[] sign(int[] x, int[] target);
		public long[] sign(long[] x, long[] target);
		public short[] sign(short[] x, short[] target);
		public float[] sign(float[] x, float[] target);
		public double[] sign(double[] x, double[] target);
		public char[] sign(char[] x, char[] target);
		public int[] sign(int[] x);
		public long[] sign(long[] x);
		public short[] sign(short[] x);
		public float[] sign(float[] x);
		public double[] sign(double[] x);
		public char[] sign(char[] x);
		public double[] signD(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] signD(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] signD(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] signD(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] signD(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] signD(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] signD(int[] x, int xOff, double[] target, int targetOff);
		public double[] signD(long[] x, int xOff, double[] target, int targetOff);
		public double[] signD(short[] x, int xOff, double[] target, int targetOff);
		public double[] signD(float[] x, int xOff, double[] target, int targetOff);
		public double[] signD(double[] x, int xOff, double[] target, int targetOff);
		public double[] signD(char[] x, int xOff, double[] target, int targetOff);
		public double[] signD(int[] x, double[] target);
		public double[] signD(long[] x, double[] target);
		public double[] signD(short[] x, double[] target);
		public double[] signD(float[] x, double[] target);
		public double[] signD(double[] x, double[] target);
		public double[] signD(char[] x, double[] target);
		public double[] signD(int[] x);
		public double[] signD(long[] x);
		public double[] signD(short[] x);
		public double[] signD(float[] x);
		public double[] signD(double[] x);
		public double[] signD(char[] x);
		public int[] nonZeroSign(int[] x, int xOff, int[] target, int targetOff, int len);
		public long[] nonZeroSign(long[] x, int xOff, long[] target, int targetOff, int len);
		public short[] nonZeroSign(short[] x, int xOff, short[] target, int targetOff, int len);
		public float[] nonZeroSign(float[] x, int xOff, float[] target, int targetOff, int len);
		public double[] nonZeroSign(double[] x, int xOff, double[] target, int targetOff, int len);
		public char[] nonZeroSign(char[] x, int xOff, char[] target, int targetOff, int len);
		public int[] nonZeroSign(int[] x, int xOff, int[] target, int targetOff);
		public long[] nonZeroSign(long[] x, int xOff, long[] target, int targetOff);
		public short[] nonZeroSign(short[] x, int xOff, short[] target, int targetOff);
		public float[] nonZeroSign(float[] x, int xOff, float[] target, int targetOff);
		public double[] nonZeroSign(double[] x, int xOff, double[] target, int targetOff);
		public char[] nonZeroSign(char[] x, int xOff, char[] target, int targetOff);
		public int[] nonZeroSign(int[] x, int[] target);
		public long[] nonZeroSign(long[] x, long[] target);
		public short[] nonZeroSign(short[] x, short[] target);
		public float[] nonZeroSign(float[] x, float[] target);
		public double[] nonZeroSign(double[] x, double[] target);
		public char[] nonZeroSign(char[] x, char[] target);
		public int[] nonZeroSign(int[] x);
		public long[] nonZeroSign(long[] x);
		public short[] nonZeroSign(short[] x);
		public float[] nonZeroSign(float[] x);
		public double[] nonZeroSign(double[] x);
		public char[] nonZeroSign(char[] x);
		public double[] nonZeroSignD(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] nonZeroSignD(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] nonZeroSignD(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] nonZeroSignD(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] nonZeroSignD(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] nonZeroSignD(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] nonZeroSignD(int[] x, int xOff, double[] target, int targetOff);
		public double[] nonZeroSignD(long[] x, int xOff, double[] target, int targetOff);
		public double[] nonZeroSignD(short[] x, int xOff, double[] target, int targetOff);
		public double[] nonZeroSignD(float[] x, int xOff, double[] target, int targetOff);
		public double[] nonZeroSignD(double[] x, int xOff, double[] target, int targetOff);
		public double[] nonZeroSignD(char[] x, int xOff, double[] target, int targetOff);
		public double[] nonZeroSignD(int[] x, double[] target);
		public double[] nonZeroSignD(long[] x, double[] target);
		public double[] nonZeroSignD(short[] x, double[] target);
		public double[] nonZeroSignD(float[] x, double[] target);
		public double[] nonZeroSignD(double[] x, double[] target);
		public double[] nonZeroSignD(char[] x, double[] target);
		public double[] nonZeroSignD(int[] x);
		public double[] nonZeroSignD(long[] x);
		public double[] nonZeroSignD(short[] x);
		public double[] nonZeroSignD(float[] x);
		public double[] nonZeroSignD(double[] x);
		public double[] nonZeroSignD(char[] x);
		public int[] floor(int[] x, int xOff, int[] target, int targetOff, int len);
		public long[] floor(long[] x, int xOff, long[] target, int targetOff, int len);
		public short[] floor(short[] x, int xOff, short[] target, int targetOff, int len);
		public float[] floor(float[] x, int xOff, float[] target, int targetOff, int len);
		public double[] floor(double[] x, int xOff, double[] target, int targetOff, int len);
		public char[] floor(char[] x, int xOff, char[] target, int targetOff, int len);
		public int[] floor(int[] x, int xOff, int[] target, int targetOff);
		public long[] floor(long[] x, int xOff, long[] target, int targetOff);
		public short[] floor(short[] x, int xOff, short[] target, int targetOff);
		public float[] floor(float[] x, int xOff, float[] target, int targetOff);
		public double[] floor(double[] x, int xOff, double[] target, int targetOff);
		public char[] floor(char[] x, int xOff, char[] target, int targetOff);
		public int[] floor(int[] x, int[] target);
		public long[] floor(long[] x, long[] target);
		public short[] floor(short[] x, short[] target);
		public float[] floor(float[] x, float[] target);
		public double[] floor(double[] x, double[] target);
		public char[] floor(char[] x, char[] target);
		public int[] floor(int[] x);
		public long[] floor(long[] x);
		public short[] floor(short[] x);
		public float[] floor(float[] x);
		public double[] floor(double[] x);
		public char[] floor(char[] x);
		public double[] floorD(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] floorD(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] floorD(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] floorD(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] floorD(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] floorD(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] floorD(int[] x, int xOff, double[] target, int targetOff);
		public double[] floorD(long[] x, int xOff, double[] target, int targetOff);
		public double[] floorD(short[] x, int xOff, double[] target, int targetOff);
		public double[] floorD(float[] x, int xOff, double[] target, int targetOff);
		public double[] floorD(double[] x, int xOff, double[] target, int targetOff);
		public double[] floorD(char[] x, int xOff, double[] target, int targetOff);
		public double[] floorD(int[] x, double[] target);
		public double[] floorD(long[] x, double[] target);
		public double[] floorD(short[] x, double[] target);
		public double[] floorD(float[] x, double[] target);
		public double[] floorD(double[] x, double[] target);
		public double[] floorD(char[] x, double[] target);
		public double[] floorD(int[] x);
		public double[] floorD(long[] x);
		public double[] floorD(short[] x);
		public double[] floorD(float[] x);
		public double[] floorD(double[] x);
		public double[] floorD(char[] x);
		public int[] ceil(int[] x, int xOff, int[] target, int targetOff, int len);
		public long[] ceil(long[] x, int xOff, long[] target, int targetOff, int len);
		public short[] ceil(short[] x, int xOff, short[] target, int targetOff, int len);
		public float[] ceil(float[] x, int xOff, float[] target, int targetOff, int len);
		public double[] ceil(double[] x, int xOff, double[] target, int targetOff, int len);
		public char[] ceil(char[] x, int xOff, char[] target, int targetOff, int len);
		public int[] ceil(int[] x, int xOff, int[] target, int targetOff);
		public long[] ceil(long[] x, int xOff, long[] target, int targetOff);
		public short[] ceil(short[] x, int xOff, short[] target, int targetOff);
		public float[] ceil(float[] x, int xOff, float[] target, int targetOff);
		public double[] ceil(double[] x, int xOff, double[] target, int targetOff);
		public char[] ceil(char[] x, int xOff, char[] target, int targetOff);
		public int[] ceil(int[] x, int[] target);
		public long[] ceil(long[] x, long[] target);
		public short[] ceil(short[] x, short[] target);
		public float[] ceil(float[] x, float[] target);
		public double[] ceil(double[] x, double[] target);
		public char[] ceil(char[] x, char[] target);
		public int[] ceil(int[] x);
		public long[] ceil(long[] x);
		public short[] ceil(short[] x);
		public float[] ceil(float[] x);
		public double[] ceil(double[] x);
		public char[] ceil(char[] x);
		public double[] ceilD(int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] ceilD(long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] ceilD(short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] ceilD(float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] ceilD(double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] ceilD(char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] ceilD(int[] x, int xOff, double[] target, int targetOff);
		public double[] ceilD(long[] x, int xOff, double[] target, int targetOff);
		public double[] ceilD(short[] x, int xOff, double[] target, int targetOff);
		public double[] ceilD(float[] x, int xOff, double[] target, int targetOff);
		public double[] ceilD(double[] x, int xOff, double[] target, int targetOff);
		public double[] ceilD(char[] x, int xOff, double[] target, int targetOff);
		public double[] ceilD(int[] x, double[] target);
		public double[] ceilD(long[] x, double[] target);
		public double[] ceilD(short[] x, double[] target);
		public double[] ceilD(float[] x, double[] target);
		public double[] ceilD(double[] x, double[] target);
		public double[] ceilD(char[] x, double[] target);
		public double[] ceilD(int[] x);
		public double[] ceilD(long[] x);
		public double[] ceilD(short[] x);
		public double[] ceilD(float[] x);
		public double[] ceilD(double[] x);
		public double[] ceilD(char[] x);
		public float[] fract(float[] x, int xOff, float[] target, int targetOff, int len);
		public double[] fract(double[] x, int xOff, double[] target, int targetOff, int len);
		public float[] fract(float[] x, int xOff, float[] target, int targetOff);
		public double[] fract(double[] x, int xOff, double[] target, int targetOff);
		public float[] fract(float[] x, float[] target);
		public double[] fract(double[] x, double[] target);
		public float[] fract(float[] x);
		public double[] fract(double[] x);
		public int[] min(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff, int len);
		public long[] min(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff, int len);
		public short[] min(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff, int len);
		public float[] min(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff, int len);
		public double[] min(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len);
		public char[] min(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff, int len);
		public int[] min(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff);
		public long[] min(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff);
		public short[] min(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff);
		public float[] min(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff);
		public double[] min(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff);
		public char[] min(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff);
		public int[] min(int[] x, int[] y, int[] target);
		public long[] min(long[] x, long[] y, long[] target);
		public short[] min(short[] x, short[] y, short[] target);
		public float[] min(float[] x, float[] y, float[] target);
		public double[] min(double[] x, double[] y, double[] target);
		public char[] min(char[] x, char[] y, char[] target);
		public int[] min(int[] x, int[] y);
		public long[] min(long[] x, long[] y);
		public short[] min(short[] x, short[] y);
		public float[] min(float[] x, float[] y);
		public double[] min(double[] x, double[] y);
		public char[] min(char[] x, char[] y);
		public int[] min(int[] x, int xOff, int y, int[] target, int targetOff, int len);
		public long[] min(long[] x, int xOff, long y, long[] target, int targetOff, int len);
		public short[] min(short[] x, int xOff, short y, short[] target, int targetOff, int len);
		public float[] min(float[] x, int xOff, float y, float[] target, int targetOff, int len);
		public double[] min(double[] x, int xOff, double y, double[] target, int targetOff, int len);
		public char[] min(char[] x, int xOff, char y, char[] target, int targetOff, int len);
		public int[] min(int[] x, int xOff, int y, int[] target, int targetOff);
		public long[] min(long[] x, int xOff, long y, long[] target, int targetOff);
		public short[] min(short[] x, int xOff, short y, short[] target, int targetOff);
		public float[] min(float[] x, int xOff, float y, float[] target, int targetOff);
		public double[] min(double[] x, int xOff, double y, double[] target, int targetOff);
		public char[] min(char[] x, int xOff, char y, char[] target, int targetOff);
		public int[] min(int[] x, int y, int[] target);
		public long[] min(long[] x, long y, long[] target);
		public short[] min(short[] x, short y, short[] target);
		public float[] min(float[] x, float y, float[] target);
		public double[] min(double[] x, double y, double[] target);
		public char[] min(char[] x, char y, char[] target);
		public int[] min(int[] x, int y);
		public long[] min(long[] x, long y);
		public short[] min(short[] x, short y);
		public float[] min(float[] x, float y);
		public double[] min(double[] x, double y);
		public char[] min(char[] x, char y);
		public double[] minD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff, int len);
		public double[] minD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff, int len);
		public double[] minD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff, int len);
		public double[] minD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff, int len);
		public double[] minD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len);
		public double[] minD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff, int len);
		public double[] minD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff);
		public double[] minD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff);
		public double[] minD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff);
		public double[] minD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff);
		public double[] minD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff);
		public double[] minD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff);
		public double[] minD(int[] x, int[] y, double[] target);
		public double[] minD(long[] x, long[] y, double[] target);
		public double[] minD(short[] x, short[] y, double[] target);
		public double[] minD(float[] x, float[] y, double[] target);
		public double[] minD(double[] x, double[] y, double[] target);
		public double[] minD(char[] x, char[] y, double[] target);
		public double[] minD(int[] x, int[] y);
		public double[] minD(long[] x, long[] y);
		public double[] minD(short[] x, short[] y);
		public double[] minD(float[] x, float[] y);
		public double[] minD(double[] x, double[] y);
		public double[] minD(char[] x, char[] y);
		public double[] minD(int[] x, int xOff, int y, double[] target, int targetOff, int len);
		public double[] minD(long[] x, int xOff, long y, double[] target, int targetOff, int len);
		public double[] minD(short[] x, int xOff, short y, double[] target, int targetOff, int len);
		public double[] minD(float[] x, int xOff, float y, double[] target, int targetOff, int len);
		public double[] minD(double[] x, int xOff, double y, double[] target, int targetOff, int len);
		public double[] minD(char[] x, int xOff, char y, double[] target, int targetOff, int len);
		public double[] minD(int[] x, int xOff, int y, double[] target, int targetOff);
		public double[] minD(long[] x, int xOff, long y, double[] target, int targetOff);
		public double[] minD(short[] x, int xOff, short y, double[] target, int targetOff);
		public double[] minD(float[] x, int xOff, float y, double[] target, int targetOff);
		public double[] minD(double[] x, int xOff, double y, double[] target, int targetOff);
		public double[] minD(char[] x, int xOff, char y, double[] target, int targetOff);
		public double[] minD(int[] x, int y, double[] target);
		public double[] minD(long[] x, long y, double[] target);
		public double[] minD(short[] x, short y, double[] target);
		public double[] minD(float[] x, float y, double[] target);
		public double[] minD(double[] x, double y, double[] target);
		public double[] minD(char[] x, char y, double[] target);
		public double[] minD(int[] x, int y);
		public double[] minD(long[] x, long y);
		public double[] minD(short[] x, short y);
		public double[] minD(float[] x, float y);
		public double[] minD(double[] x, double y);
		public double[] minD(char[] x, char y);
		public int[] max(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff, int len);
		public long[] max(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff, int len);
		public short[] max(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff, int len);
		public float[] max(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff, int len);
		public double[] max(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len);
		public char[] max(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff, int len);
		public int[] max(int[] x, int xOff, int[] y, int yOff, int[] target, int targetOff);
		public long[] max(long[] x, int xOff, long[] y, int yOff, long[] target, int targetOff);
		public short[] max(short[] x, int xOff, short[] y, int yOff, short[] target, int targetOff);
		public float[] max(float[] x, int xOff, float[] y, int yOff, float[] target, int targetOff);
		public double[] max(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff);
		public char[] max(char[] x, int xOff, char[] y, int yOff, char[] target, int targetOff);
		public int[] max(int[] x, int[] y, int[] target);
		public long[] max(long[] x, long[] y, long[] target);
		public short[] max(short[] x, short[] y, short[] target);
		public float[] max(float[] x, float[] y, float[] target);
		public double[] max(double[] x, double[] y, double[] target);
		public char[] max(char[] x, char[] y, char[] target);
		public int[] max(int[] x, int[] y);
		public long[] max(long[] x, long[] y);
		public short[] max(short[] x, short[] y);
		public float[] max(float[] x, float[] y);
		public double[] max(double[] x, double[] y);
		public char[] max(char[] x, char[] y);
		public int[] max(int[] x, int xOff, int y, int[] target, int targetOff, int len);
		public long[] max(long[] x, int xOff, long y, long[] target, int targetOff, int len);
		public short[] max(short[] x, int xOff, short y, short[] target, int targetOff, int len);
		public float[] max(float[] x, int xOff, float y, float[] target, int targetOff, int len);
		public double[] max(double[] x, int xOff, double y, double[] target, int targetOff, int len);
		public char[] max(char[] x, int xOff, char y, char[] target, int targetOff, int len);
		public int[] max(int[] x, int xOff, int y, int[] target, int targetOff);
		public long[] max(long[] x, int xOff, long y, long[] target, int targetOff);
		public short[] max(short[] x, int xOff, short y, short[] target, int targetOff);
		public float[] max(float[] x, int xOff, float y, float[] target, int targetOff);
		public double[] max(double[] x, int xOff, double y, double[] target, int targetOff);
		public char[] max(char[] x, int xOff, char y, char[] target, int targetOff);
		public int[] max(int[] x, int y, int[] target);
		public long[] max(long[] x, long y, long[] target);
		public short[] max(short[] x, short y, short[] target);
		public float[] max(float[] x, float y, float[] target);
		public double[] max(double[] x, double y, double[] target);
		public char[] max(char[] x, char y, char[] target);
		public int[] max(int[] x, int y);
		public long[] max(long[] x, long y);
		public short[] max(short[] x, short y);
		public float[] max(float[] x, float y);
		public double[] max(double[] x, double y);
		public char[] max(char[] x, char y);
		public double[] maxD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff, int len);
		public double[] maxD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff, int len);
		public double[] maxD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff, int len);
		public double[] maxD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff, int len);
		public double[] maxD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff, int len);
		public double[] maxD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff, int len);
		public double[] maxD(int[] x, int xOff, int[] y, int yOff, double[] target, int targetOff);
		public double[] maxD(long[] x, int xOff, long[] y, int yOff, double[] target, int targetOff);
		public double[] maxD(short[] x, int xOff, short[] y, int yOff, double[] target, int targetOff);
		public double[] maxD(float[] x, int xOff, float[] y, int yOff, double[] target, int targetOff);
		public double[] maxD(double[] x, int xOff, double[] y, int yOff, double[] target, int targetOff);
		public double[] maxD(char[] x, int xOff, char[] y, int yOff, double[] target, int targetOff);
		public double[] maxD(int[] x, int[] y, double[] target);
		public double[] maxD(long[] x, long[] y, double[] target);
		public double[] maxD(short[] x, short[] y, double[] target);
		public double[] maxD(float[] x, float[] y, double[] target);
		public double[] maxD(double[] x, double[] y, double[] target);
		public double[] maxD(char[] x, char[] y, double[] target);
		public double[] maxD(int[] x, int[] y);
		public double[] maxD(long[] x, long[] y);
		public double[] maxD(short[] x, short[] y);
		public double[] maxD(float[] x, float[] y);
		public double[] maxD(double[] x, double[] y);
		public double[] maxD(char[] x, char[] y);
		public double[] maxD(int[] x, int xOff, int y, double[] target, int targetOff, int len);
		public double[] maxD(long[] x, int xOff, long y, double[] target, int targetOff, int len);
		public double[] maxD(short[] x, int xOff, short y, double[] target, int targetOff, int len);
		public double[] maxD(float[] x, int xOff, float y, double[] target, int targetOff, int len);
		public double[] maxD(double[] x, int xOff, double y, double[] target, int targetOff, int len);
		public double[] maxD(char[] x, int xOff, char y, double[] target, int targetOff, int len);
		public double[] maxD(int[] x, int xOff, int y, double[] target, int targetOff);
		public double[] maxD(long[] x, int xOff, long y, double[] target, int targetOff);
		public double[] maxD(short[] x, int xOff, short y, double[] target, int targetOff);
		public double[] maxD(float[] x, int xOff, float y, double[] target, int targetOff);
		public double[] maxD(double[] x, int xOff, double y, double[] target, int targetOff);
		public double[] maxD(char[] x, int xOff, char y, double[] target, int targetOff);
		public double[] maxD(int[] x, int y, double[] target);
		public double[] maxD(long[] x, long y, double[] target);
		public double[] maxD(short[] x, short y, double[] target);
		public double[] maxD(float[] x, float y, double[] target);
		public double[] maxD(double[] x, double y, double[] target);
		public double[] maxD(char[] x, char y, double[] target);
		public double[] maxD(int[] x, int y);
		public double[] maxD(long[] x, long y);
		public double[] maxD(short[] x, short y);
		public double[] maxD(float[] x, float y);
		public double[] maxD(double[] x, double y);
		public double[] maxD(char[] x, char y);
		public int[] clamp(int[] x, int xOff, int[] minVal, int minValOff, int[] maxVal, int maxValOff, int[] target, int targetOff, int len);
		public long[] clamp(long[] x, int xOff, long[] minVal, int minValOff, long[] maxVal, int maxValOff, long[] target, int targetOff, int len);
		public short[] clamp(short[] x, int xOff, short[] minVal, int minValOff, short[] maxVal, int maxValOff, short[] target, int targetOff, int len);
		public float[] clamp(float[] x, int xOff, float[] minVal, int minValOff, float[] maxVal, int maxValOff, float[] target, int targetOff, int len);
		public double[] clamp(double[] x, int xOff, double[] minVal, int minValOff, double[] maxVal, int maxValOff, double[] target, int targetOff, int len);
		public char[] clamp(char[] x, int xOff, char[] minVal, int minValOff, char[] maxVal, int maxValOff, char[] target, int targetOff, int len);
		public int[] clamp(int[] x, int xOff, int[] minVal, int minValOff, int[] maxVal, int maxValOff, int[] target, int targetOff);
		public long[] clamp(long[] x, int xOff, long[] minVal, int minValOff, long[] maxVal, int maxValOff, long[] target, int targetOff);
		public short[] clamp(short[] x, int xOff, short[] minVal, int minValOff, short[] maxVal, int maxValOff, short[] target, int targetOff);
		public float[] clamp(float[] x, int xOff, float[] minVal, int minValOff, float[] maxVal, int maxValOff, float[] target, int targetOff);
		public double[] clamp(double[] x, int xOff, double[] minVal, int minValOff, double[] maxVal, int maxValOff, double[] target, int targetOff);
		public char[] clamp(char[] x, int xOff, char[] minVal, int minValOff, char[] maxVal, int maxValOff, char[] target, int targetOff);
		public int[] clamp(int[] x, int[] minVal, int[] maxVal, int[] target);
		public long[] clamp(long[] x, long[] minVal, long[] maxVal, long[] target);
		public short[] clamp(short[] x, short[] minVal, short[] maxVal, short[] target);
		public float[] clamp(float[] x, float[] minVal, float[] maxVal, float[] target);
		public double[] clamp(double[] x, double[] minVal, double[] maxVal, double[] target);
		public char[] clamp(char[] x, char[] minVal, char[] maxVal, char[] target);
		public int[] clamp(int[] x, int[] minVal, int[] maxVal);
		public long[] clamp(long[] x, long[] minVal, long[] maxVal);
		public short[] clamp(short[] x, short[] minVal, short[] maxVal);
		public float[] clamp(float[] x, float[] minVal, float[] maxVal);
		public double[] clamp(double[] x, double[] minVal, double[] maxVal);
		public char[] clamp(char[] x, char[] minVal, char[] maxVal);
		public int[] clamp(int[] x, int xOff, int minVal, int maxVal, int[] target, int targetOff, int len);
		public long[] clamp(long[] x, int xOff, long minVal, long maxVal, long[] target, int targetOff, int len);
		public short[] clamp(short[] x, int xOff, short minVal, short maxVal, short[] target, int targetOff, int len);
		public float[] clamp(float[] x, int xOff, float minVal, float maxVal, float[] target, int targetOff, int len);
		public double[] clamp(double[] x, int xOff, double minVal, double maxVal, double[] target, int targetOff, int len);
		public char[] clamp(char[] x, int xOff, char minVal, char maxVal, char[] target, int targetOff, int len);
		public int[] clamp(int[] x, int xOff, int minVal, int maxVal, int[] target, int targetOff);
		public long[] clamp(long[] x, int xOff, long minVal, long maxVal, long[] target, int targetOff);
		public short[] clamp(short[] x, int xOff, short minVal, short maxVal, short[] target, int targetOff);
		public float[] clamp(float[] x, int xOff, float minVal, float maxVal, float[] target, int targetOff);
		public double[] clamp(double[] x, int xOff, double minVal, double maxVal, double[] target, int targetOff);
		public char[] clamp(char[] x, int xOff, char minVal, char maxVal, char[] target, int targetOff);
		public int[] clamp(int[] x, int minVal, int maxVal, int[] target);
		public long[] clamp(long[] x, long minVal, long maxVal, long[] target);
		public short[] clamp(short[] x, short minVal, short maxVal, short[] target);
		public float[] clamp(float[] x, float minVal, float maxVal, float[] target);
		public double[] clamp(double[] x, double minVal, double maxVal, double[] target);
		public char[] clamp(char[] x, char minVal, char maxVal, char[] target);
		public int[] clamp(int[] x, int minVal, int maxVal);
		public long[] clamp(long[] x, long minVal, long maxVal);
		public short[] clamp(short[] x, short minVal, short maxVal);
		public float[] clamp(float[] x, float minVal, float maxVal);
		public double[] clamp(double[] x, double minVal, double maxVal);
		public char[] clamp(char[] x, char minVal, char maxVal);
		public double[] clampD(int[] x, int xOff, int[] minVal, int minValOff, int[] maxVal, int maxValOff, double[] target, int targetOff, int len);
		public double[] clampD(long[] x, int xOff, long[] minVal, int minValOff, long[] maxVal, int maxValOff, double[] target, int targetOff, int len);
		public double[] clampD(short[] x, int xOff, short[] minVal, int minValOff, short[] maxVal, int maxValOff, double[] target, int targetOff, int len);
		public double[] clampD(float[] x, int xOff, float[] minVal, int minValOff, float[] maxVal, int maxValOff, double[] target, int targetOff, int len);
		public double[] clampD(double[] x, int xOff, double[] minVal, int minValOff, double[] maxVal, int maxValOff, double[] target, int targetOff, int len);
		public double[] clampD(char[] x, int xOff, char[] minVal, int minValOff, char[] maxVal, int maxValOff, double[] target, int targetOff, int len);
		public double[] clampD(int[] x, int xOff, int[] minVal, int minValOff, int[] maxVal, int maxValOff, double[] target, int targetOff);
		public double[] clampD(long[] x, int xOff, long[] minVal, int minValOff, long[] maxVal, int maxValOff, double[] target, int targetOff);
		public double[] clampD(short[] x, int xOff, short[] minVal, int minValOff, short[] maxVal, int maxValOff, double[] target, int targetOff);
		public double[] clampD(float[] x, int xOff, float[] minVal, int minValOff, float[] maxVal, int maxValOff, double[] target, int targetOff);
		public double[] clampD(double[] x, int xOff, double[] minVal, int minValOff, double[] maxVal, int maxValOff, double[] target, int targetOff);
		public double[] clampD(char[] x, int xOff, char[] minVal, int minValOff, char[] maxVal, int maxValOff, double[] target, int targetOff);
		public double[] clampD(int[] x, int[] minVal, int[] maxVal, double[] target);
		public double[] clampD(long[] x, long[] minVal, long[] maxVal, double[] target);
		public double[] clampD(short[] x, short[] minVal, short[] maxVal, double[] target);
		public double[] clampD(float[] x, float[] minVal, float[] maxVal, double[] target);
		public double[] clampD(double[] x, double[] minVal, double[] maxVal, double[] target);
		public double[] clampD(char[] x, char[] minVal, char[] maxVal, double[] target);
		public double[] clampD(int[] x, int[] minVal, int[] maxVal);
		public double[] clampD(long[] x, long[] minVal, long[] maxVal);
		public double[] clampD(short[] x, short[] minVal, short[] maxVal);
		public double[] clampD(float[] x, float[] minVal, float[] maxVal);
		public double[] clampD(double[] x, double[] minVal, double[] maxVal);
		public double[] clampD(char[] x, char[] minVal, char[] maxVal);
		public double[] clampD(int[] x, int xOff, int minVal, int maxVal, double[] target, int targetOff, int len);
		public double[] clampD(long[] x, int xOff, long minVal, long maxVal, double[] target, int targetOff, int len);
		public double[] clampD(short[] x, int xOff, short minVal, short maxVal, double[] target, int targetOff, int len);
		public double[] clampD(float[] x, int xOff, float minVal, float maxVal, double[] target, int targetOff, int len);
		public double[] clampD(double[] x, int xOff, double minVal, double maxVal, double[] target, int targetOff, int len);
		public double[] clampD(char[] x, int xOff, char minVal, char maxVal, double[] target, int targetOff, int len);
		public double[] clampD(int[] x, int xOff, int minVal, int maxVal, double[] target, int targetOff);
		public double[] clampD(long[] x, int xOff, long minVal, long maxVal, double[] target, int targetOff);
		public double[] clampD(short[] x, int xOff, short minVal, short maxVal, double[] target, int targetOff);
		public double[] clampD(float[] x, int xOff, float minVal, float maxVal, double[] target, int targetOff);
		public double[] clampD(double[] x, int xOff, double minVal, double maxVal, double[] target, int targetOff);
		public double[] clampD(char[] x, int xOff, char minVal, char maxVal, double[] target, int targetOff);
		public double[] clampD(int[] x, int minVal, int maxVal, double[] target);
		public double[] clampD(long[] x, long minVal, long maxVal, double[] target);
		public double[] clampD(short[] x, short minVal, short maxVal, double[] target);
		public double[] clampD(float[] x, float minVal, float maxVal, double[] target);
		public double[] clampD(double[] x, double minVal, double maxVal, double[] target);
		public double[] clampD(char[] x, char minVal, char maxVal, double[] target);
		public double[] clampD(int[] x, int minVal, int maxVal);
		public double[] clampD(long[] x, long minVal, long maxVal);
		public double[] clampD(short[] x, short minVal, short maxVal);
		public double[] clampD(float[] x, float minVal, float maxVal);
		public double[] clampD(double[] x, double minVal, double maxVal);
		public double[] clampD(char[] x, char minVal, char maxVal);
		public double[] mix(int[] x, int xOff, int[] y, int yOff, int[] a, int aOff, double[] target, int targetOff, int len);
		public double[] mix(long[] x, int xOff, long[] y, int yOff, long[] a, int aOff, double[] target, int targetOff, int len);
		public double[] mix(short[] x, int xOff, short[] y, int yOff, short[] a, int aOff, double[] target, int targetOff, int len);
		public double[] mix(float[] x, int xOff, float[] y, int yOff, float[] a, int aOff, double[] target, int targetOff, int len);
		public double[] mix(double[] x, int xOff, double[] y, int yOff, double[] a, int aOff, double[] target, int targetOff, int len);
		public double[] mix(char[] x, int xOff, char[] y, int yOff, char[] a, int aOff, double[] target, int targetOff, int len);
		public double[] mix(int[] x, int xOff, int[] y, int yOff, int[] a, int aOff, double[] target, int targetOff);
		public double[] mix(long[] x, int xOff, long[] y, int yOff, long[] a, int aOff, double[] target, int targetOff);
		public double[] mix(short[] x, int xOff, short[] y, int yOff, short[] a, int aOff, double[] target, int targetOff);
		public double[] mix(float[] x, int xOff, float[] y, int yOff, float[] a, int aOff, double[] target, int targetOff);
		public double[] mix(double[] x, int xOff, double[] y, int yOff, double[] a, int aOff, double[] target, int targetOff);
		public double[] mix(char[] x, int xOff, char[] y, int yOff, char[] a, int aOff, double[] target, int targetOff);
		public double[] mix(int[] x, int[] y, int[] a, double[] target);
		public double[] mix(long[] x, long[] y, long[] a, double[] target);
		public double[] mix(short[] x, short[] y, short[] a, double[] target);
		public double[] mix(float[] x, float[] y, float[] a, double[] target);
		public double[] mix(double[] x, double[] y, double[] a, double[] target);
		public double[] mix(char[] x, char[] y, char[] a, double[] target);
		public double[] mix(int[] x, int[] y, int[] a);
		public double[] mix(long[] x, long[] y, long[] a);
		public double[] mix(short[] x, short[] y, short[] a);
		public double[] mix(float[] x, float[] y, float[] a);
		public double[] mix(double[] x, double[] y, double[] a);
		public double[] mix(char[] x, char[] y, char[] a);
		public double[] mix(int[] x, int xOff, int[] y, int yOff, int a, double[] target, int targetOff, int len);
		public double[] mix(long[] x, int xOff, long[] y, int yOff, long a, double[] target, int targetOff, int len);
		public double[] mix(short[] x, int xOff, short[] y, int yOff, short a, double[] target, int targetOff, int len);
		public double[] mix(float[] x, int xOff, float[] y, int yOff, float a, double[] target, int targetOff, int len);
		public double[] mix(double[] x, int xOff, double[] y, int yOff, double a, double[] target, int targetOff, int len);
		public double[] mix(char[] x, int xOff, char[] y, int yOff, char a, double[] target, int targetOff, int len);
		public double[] mix(int[] x, int xOff, int[] y, int yOff, int a, double[] target, int targetOff);
		public double[] mix(long[] x, int xOff, long[] y, int yOff, long a, double[] target, int targetOff);
		public double[] mix(short[] x, int xOff, short[] y, int yOff, short a, double[] target, int targetOff);
		public double[] mix(float[] x, int xOff, float[] y, int yOff, float a, double[] target, int targetOff);
		public double[] mix(double[] x, int xOff, double[] y, int yOff, double a, double[] target, int targetOff);
		public double[] mix(char[] x, int xOff, char[] y, int yOff, char a, double[] target, int targetOff);
		public double[] mix(int[] x, int[] y, int a, double[] target);
		public double[] mix(long[] x, long[] y, long a, double[] target);
		public double[] mix(short[] x, short[] y, short a, double[] target);
		public double[] mix(float[] x, float[] y, float a, double[] target);
		public double[] mix(double[] x, double[] y, double a, double[] target);
		public double[] mix(char[] x, char[] y, char a, double[] target);
		public double[] mix(int[] x, int[] y, int a);
		public double[] mix(long[] x, long[] y, long a);
		public double[] mix(short[] x, short[] y, short a);
		public double[] mix(float[] x, float[] y, float a);
		public double[] mix(double[] x, double[] y, double a);
		public double[] mix(char[] x, char[] y, char a);
		public double[] step(int[] x, int xOff, int[] edge, int edgeOff, double[] target, int targetOff, int len);
		public double[] step(long[] x, int xOff, long[] edge, int edgeOff, double[] target, int targetOff, int len);
		public double[] step(short[] x, int xOff, short[] edge, int edgeOff, double[] target, int targetOff, int len);
		public double[] step(float[] x, int xOff, float[] edge, int edgeOff, double[] target, int targetOff, int len);
		public double[] step(double[] x, int xOff, double[] edge, int edgeOff, double[] target, int targetOff, int len);
		public double[] step(char[] x, int xOff, char[] edge, int edgeOff, double[] target, int targetOff, int len);
		public double[] step(int[] x, int xOff, int[] edge, int edgeOff, double[] target, int targetOff);
		public double[] step(long[] x, int xOff, long[] edge, int edgeOff, double[] target, int targetOff);
		public double[] step(short[] x, int xOff, short[] edge, int edgeOff, double[] target, int targetOff);
		public double[] step(float[] x, int xOff, float[] edge, int edgeOff, double[] target, int targetOff);
		public double[] step(double[] x, int xOff, double[] edge, int edgeOff, double[] target, int targetOff);
		public double[] step(char[] x, int xOff, char[] edge, int edgeOff, double[] target, int targetOff);
		public double[] step(int[] x, int[] edge, double[] target);
		public double[] step(long[] x, long[] edge, double[] target);
		public double[] step(short[] x, short[] edge, double[] target);
		public double[] step(float[] x, float[] edge, double[] target);
		public double[] step(double[] x, double[] edge, double[] target);
		public double[] step(char[] x, char[] edge, double[] target);
		public double[] step(int[] x, int[] edge);
		public double[] step(long[] x, long[] edge);
		public double[] step(short[] x, short[] edge);
		public double[] step(float[] x, float[] edge);
		public double[] step(double[] x, double[] edge);
		public double[] step(char[] x, char[] edge);
		public double[] step(int[] x, int xOff, int edge, double[] target, int targetOff, int len);
		public double[] step(long[] x, int xOff, long edge, double[] target, int targetOff, int len);
		public double[] step(short[] x, int xOff, short edge, double[] target, int targetOff, int len);
		public double[] step(float[] x, int xOff, float edge, double[] target, int targetOff, int len);
		public double[] step(double[] x, int xOff, double edge, double[] target, int targetOff, int len);
		public double[] step(char[] x, int xOff, char edge, double[] target, int targetOff, int len);
		public double[] step(int[] x, int xOff, int edge, double[] target, int targetOff);
		public double[] step(long[] x, int xOff, long edge, double[] target, int targetOff);
		public double[] step(short[] x, int xOff, short edge, double[] target, int targetOff);
		public double[] step(float[] x, int xOff, float edge, double[] target, int targetOff);
		public double[] step(double[] x, int xOff, double edge, double[] target, int targetOff);
		public double[] step(char[] x, int xOff, char edge, double[] target, int targetOff);
		public double[] step(int[] x, int edge, double[] target);
		public double[] step(long[] x, long edge, double[] target);
		public double[] step(short[] x, short edge, double[] target);
		public double[] step(float[] x, float edge, double[] target);
		public double[] step(double[] x, double edge, double[] target);
		public double[] step(char[] x, char edge, double[] target);
		public double[] step(int[] x, int edge);
		public double[] step(long[] x, long edge);
		public double[] step(short[] x, short edge);
		public double[] step(float[] x, float edge);
		public double[] step(double[] x, double edge);
		public double[] step(char[] x, char edge);
		public double[] smoothstep(int[] edge0, int edge0Off, int[] edge1, int edge1Off, int[] x, int xOff, double[] target, int targetOff, int len);
		public double[] smoothstep(long[] edge0, int edge0Off, long[] edge1, int edge1Off, long[] x, int xOff, double[] target, int targetOff, int len);
		public double[] smoothstep(short[] edge0, int edge0Off, short[] edge1, int edge1Off, short[] x, int xOff, double[] target, int targetOff, int len);
		public double[] smoothstep(float[] edge0, int edge0Off, float[] edge1, int edge1Off, float[] x, int xOff, double[] target, int targetOff, int len);
		public double[] smoothstep(double[] edge0, int edge0Off, double[] edge1, int edge1Off, double[] x, int xOff, double[] target, int targetOff, int len);
		public double[] smoothstep(char[] edge0, int edge0Off, char[] edge1, int edge1Off, char[] x, int xOff, double[] target, int targetOff, int len);
		public double[] smoothstep(int[] edge0, int edge0Off, int[] edge1, int edge1Off, int[] x, int xOff, double[] target, int targetOff);
		public double[] smoothstep(long[] edge0, int edge0Off, long[] edge1, int edge1Off, long[] x, int xOff, double[] target, int targetOff);
		public double[] smoothstep(short[] edge0, int edge0Off, short[] edge1, int edge1Off, short[] x, int xOff, double[] target, int targetOff);
		public double[] smoothstep(float[] edge0, int edge0Off, float[] edge1, int edge1Off, float[] x, int xOff, double[] target, int targetOff);
		public double[] smoothstep(double[] edge0, int edge0Off, double[] edge1, int edge1Off, double[] x, int xOff, double[] target, int targetOff);
		public double[] smoothstep(char[] edge0, int edge0Off, char[] edge1, int edge1Off, char[] x, int xOff, double[] target, int targetOff);
		public double[] smoothstep(int[] edge0, int[] edge1, int[] x, double[] target);
		public double[] smoothstep(long[] edge0, long[] edge1, long[] x, double[] target);
		public double[] smoothstep(short[] edge0, short[] edge1, short[] x, double[] target);
		public double[] smoothstep(float[] edge0, float[] edge1, float[] x, double[] target);
		public double[] smoothstep(double[] edge0, double[] edge1, double[] x, double[] target);
		public double[] smoothstep(char[] edge0, char[] edge1, char[] x, double[] target);
		public double[] smoothstep(int[] edge0, int[] edge1, int[] x);
		public double[] smoothstep(long[] edge0, long[] edge1, long[] x);
		public double[] smoothstep(short[] edge0, short[] edge1, short[] x);
		public double[] smoothstep(float[] edge0, float[] edge1, float[] x);
		public double[] smoothstep(double[] edge0, double[] edge1, double[] x);
		public double[] smoothstep(char[] edge0, char[] edge1, char[] x);
		public double[] smoothstep(int[] edge0, int edge0Off, int[] edge1, int edge1Off, int x, double[] target, int targetOff, int len);
		public double[] smoothstep(long[] edge0, int edge0Off, long[] edge1, int edge1Off, long x, double[] target, int targetOff, int len);
		public double[] smoothstep(short[] edge0, int edge0Off, short[] edge1, int edge1Off, short x, double[] target, int targetOff, int len);
		public double[] smoothstep(float[] edge0, int edge0Off, float[] edge1, int edge1Off, float x, double[] target, int targetOff, int len);
		public double[] smoothstep(double[] edge0, int edge0Off, double[] edge1, int edge1Off, double x, double[] target, int targetOff, int len);
		public double[] smoothstep(char[] edge0, int edge0Off, char[] edge1, int edge1Off, char x, double[] target, int targetOff, int len);
		public double[] smoothstep(int[] edge0, int edge0Off, int[] edge1, int edge1Off, int x, double[] target, int targetOff);
		public double[] smoothstep(long[] edge0, int edge0Off, long[] edge1, int edge1Off, long x, double[] target, int targetOff);
		public double[] smoothstep(short[] edge0, int edge0Off, short[] edge1, int edge1Off, short x, double[] target, int targetOff);
		public double[] smoothstep(float[] edge0, int edge0Off, float[] edge1, int edge1Off, float x, double[] target, int targetOff);
		public double[] smoothstep(double[] edge0, int edge0Off, double[] edge1, int edge1Off, double x, double[] target, int targetOff);
		public double[] smoothstep(char[] edge0, int edge0Off, char[] edge1, int edge1Off, char x, double[] target, int targetOff);
		public double[] smoothstep(int[] edge0, int[] edge1, int x, double[] target);
		public double[] smoothstep(long[] edge0, long[] edge1, long x, double[] target);
		public double[] smoothstep(short[] edge0, short[] edge1, short x, double[] target);
		public double[] smoothstep(float[] edge0, float[] edge1, float x, double[] target);
		public double[] smoothstep(double[] edge0, double[] edge1, double x, double[] target);
		public double[] smoothstep(char[] edge0, char[] edge1, char x, double[] target);
		public double[] smoothstep(int[] edge0, int[] edge1, int x);
		public double[] smoothstep(long[] edge0, long[] edge1, long x);
		public double[] smoothstep(short[] edge0, short[] edge1, short x);
		public double[] smoothstep(float[] edge0, float[] edge1, float x);
		public double[] smoothstep(double[] edge0, double[] edge1, double x);
		public double[] smoothstep(char[] edge0, char[] edge1, char x);
		public double[] ease(int t, int d, int[] b, int bOff, int[] c, int cOff, Easing easing, double[] target, int targetOff, int len);
		public double[] ease(long t, long d, long[] b, int bOff, long[] c, int cOff, Easing easing, double[] target, int targetOff, int len);
		public double[] ease(short t, short d, short[] b, int bOff, short[] c, int cOff, Easing easing, double[] target, int targetOff, int len);
		public double[] ease(float t, float d, float[] b, int bOff, float[] c, int cOff, Easing easing, double[] target, int targetOff, int len);
		public double[] ease(double t, double d, double[] b, int bOff, double[] c, int cOff, Easing easing, double[] target, int targetOff, int len);
		public double[] ease(char t, char d, char[] b, int bOff, char[] c, int cOff, Easing easing, double[] target, int targetOff, int len);
		public double[] ease(int t, int d, int[] b, int bOff, int[] c, int cOff, Easing easing, double[] target, int targetOff);
		public double[] ease(long t, long d, long[] b, int bOff, long[] c, int cOff, Easing easing, double[] target, int targetOff);
		public double[] ease(short t, short d, short[] b, int bOff, short[] c, int cOff, Easing easing, double[] target, int targetOff);
		public double[] ease(float t, float d, float[] b, int bOff, float[] c, int cOff, Easing easing, double[] target, int targetOff);
		public double[] ease(double t, double d, double[] b, int bOff, double[] c, int cOff, Easing easing, double[] target, int targetOff);
		public double[] ease(char t, char d, char[] b, int bOff, char[] c, int cOff, Easing easing, double[] target, int targetOff);
		public double[] ease(int t, int d, int[] b, int[] c, Easing easing, double[] target);
		public double[] ease(long t, long d, long[] b, long[] c, Easing easing, double[] target);
		public double[] ease(short t, short d, short[] b, short[] c, Easing easing, double[] target);
		public double[] ease(float t, float d, float[] b, float[] c, Easing easing, double[] target);
		public double[] ease(double t, double d, double[] b, double[] c, Easing easing, double[] target);
		public double[] ease(char t, char d, char[] b, char[] c, Easing easing, double[] target);
		public double[] ease(int t, int d, int[] b, int[] c, Easing easing);
		public double[] ease(long t, long d, long[] b, long[] c, Easing easing);
		public double[] ease(short t, short d, short[] b, short[] c, Easing easing);
		public double[] ease(float t, float d, float[] b, float[] c, Easing easing);
		public double[] ease(double t, double d, double[] b, double[] c, Easing easing);
		public double[] ease(char t, char d, char[] b, char[] c, Easing easing);
		public double[] distance(int[] p0, int p0Off, int[] p1, int p1Off, double[] target, int targetOff, int len);
		public double[] distance(long[] p0, int p0Off, long[] p1, int p1Off, double[] target, int targetOff, int len);
		public double[] distance(short[] p0, int p0Off, short[] p1, int p1Off, double[] target, int targetOff, int len);
		public double[] distance(float[] p0, int p0Off, float[] p1, int p1Off, double[] target, int targetOff, int len);
		public double[] distance(double[] p0, int p0Off, double[] p1, int p1Off, double[] target, int targetOff, int len);
		public double[] distance(char[] p0, int p0Off, char[] p1, int p1Off, double[] target, int targetOff, int len);
		public double[] distance(int[] p0, int p0Off, int[] p1, int p1Off, double[] target, int targetOff);
		public double[] distance(long[] p0, int p0Off, long[] p1, int p1Off, double[] target, int targetOff);
		public double[] distance(short[] p0, int p0Off, short[] p1, int p1Off, double[] target, int targetOff);
		public double[] distance(float[] p0, int p0Off, float[] p1, int p1Off, double[] target, int targetOff);
		public double[] distance(double[] p0, int p0Off, double[] p1, int p1Off, double[] target, int targetOff);
		public double[] distance(char[] p0, int p0Off, char[] p1, int p1Off, double[] target, int targetOff);
		public double[] distance(int[] p0, int[] p1, double[] target);
		public double[] distance(long[] p0, long[] p1, double[] target);
		public double[] distance(short[] p0, short[] p1, double[] target);
		public double[] distance(float[] p0, float[] p1, double[] target);
		public double[] distance(double[] p0, double[] p1, double[] target);
		public double[] distance(char[] p0, char[] p1, double[] target);
		public double[] distance(int[] p0, int[] p1);
		public double[] distance(long[] p0, long[] p1);
		public double[] distance(short[] p0, short[] p1);
		public double[] distance(float[] p0, float[] p1);
		public double[] distance(double[] p0, double[] p1);
		public double[] distance(char[] p0, char[] p1);
		public double[] refract(int[] I, int IOff, int[] N, int NOff, int eta, double[] target, int targetOff, int len);
		public double[] refract(long[] I, int IOff, long[] N, int NOff, long eta, double[] target, int targetOff, int len);
		public double[] refract(short[] I, int IOff, short[] N, int NOff, short eta, double[] target, int targetOff, int len);
		public double[] refract(float[] I, int IOff, float[] N, int NOff, float eta, double[] target, int targetOff, int len);
		public double[] refract(double[] I, int IOff, double[] N, int NOff, double eta, double[] target, int targetOff, int len);
		public double[] refract(char[] I, int IOff, char[] N, int NOff, char eta, double[] target, int targetOff, int len);
		public double[] refract(int[] I, int IOff, int[] N, int NOff, int eta, double[] target, int targetOff);
		public double[] refract(long[] I, int IOff, long[] N, int NOff, long eta, double[] target, int targetOff);
		public double[] refract(short[] I, int IOff, short[] N, int NOff, short eta, double[] target, int targetOff);
		public double[] refract(float[] I, int IOff, float[] N, int NOff, float eta, double[] target, int targetOff);
		public double[] refract(double[] I, int IOff, double[] N, int NOff, double eta, double[] target, int targetOff);
		public double[] refract(char[] I, int IOff, char[] N, int NOff, char eta, double[] target, int targetOff);
		public double[] refract(int[] I, int[] N, int eta, double[] target);
		public double[] refract(long[] I, long[] N, long eta, double[] target);
		public double[] refract(short[] I, short[] N, short eta, double[] target);
		public double[] refract(float[] I, float[] N, float eta, double[] target);
		public double[] refract(double[] I, double[] N, double eta, double[] target);
		public double[] refract(char[] I, char[] N, char eta, double[] target);
		public double[] refract(int[] I, int[] N, int eta);
		public double[] refract(long[] I, long[] N, long eta);
		public double[] refract(short[] I, short[] N, short eta);
		public double[] refract(float[] I, float[] N, float eta);
		public double[] refract(double[] I, double[] N, double eta);
		public double[] refract(char[] I, char[] N, char eta);
		public int[] fold2(int[] fold, int foldOff, int[] target, int targetOff, int len);
		public long[] fold2(long[] fold, int foldOff, long[] target, int targetOff, int len);
		public short[] fold2(short[] fold, int foldOff, short[] target, int targetOff, int len);
		public float[] fold2(float[] fold, int foldOff, float[] target, int targetOff, int len);
		public double[] fold2(double[] fold, int foldOff, double[] target, int targetOff, int len);
		public char[] fold2(char[] fold, int foldOff, char[] target, int targetOff, int len);
		public int[] fold2(int[] fold, int foldOff, int[] target, int targetOff);
		public long[] fold2(long[] fold, int foldOff, long[] target, int targetOff);
		public short[] fold2(short[] fold, int foldOff, short[] target, int targetOff);
		public float[] fold2(float[] fold, int foldOff, float[] target, int targetOff);
		public double[] fold2(double[] fold, int foldOff, double[] target, int targetOff);
		public char[] fold2(char[] fold, int foldOff, char[] target, int targetOff);
		public int[] fold2(int[] fold, int[] target);
		public long[] fold2(long[] fold, long[] target);
		public short[] fold2(short[] fold, short[] target);
		public float[] fold2(float[] fold, float[] target);
		public double[] fold2(double[] fold, double[] target);
		public char[] fold2(char[] fold, char[] target);
		public int[] fold2(int[] fold);
		public long[] fold2(long[] fold);
		public short[] fold2(short[] fold);
		public float[] fold2(float[] fold);
		public double[] fold2(double[] fold);
		public char[] fold2(char[] fold);
		public boolean[] lessThan(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThan(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThan(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThan(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThan(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThan(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThan(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThan(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThan(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThan(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThan(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThan(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThan(int[] x, int[] y, boolean[] target);
		public boolean[] lessThan(long[] x, long[] y, boolean[] target);
		public boolean[] lessThan(short[] x, short[] y, boolean[] target);
		public boolean[] lessThan(float[] x, float[] y, boolean[] target);
		public boolean[] lessThan(double[] x, double[] y, boolean[] target);
		public boolean[] lessThan(char[] x, char[] y, boolean[] target);
		public boolean[] lessThan(int[] x, int[] y);
		public boolean[] lessThan(long[] x, long[] y);
		public boolean[] lessThan(short[] x, short[] y);
		public boolean[] lessThan(float[] x, float[] y);
		public boolean[] lessThan(double[] x, double[] y);
		public boolean[] lessThan(char[] x, char[] y);
		public boolean[] lessThanEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThanEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThanEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThanEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThanEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThanEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] lessThanEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThanEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThanEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThanEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThanEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThanEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] lessThanEqual(int[] x, int[] y, boolean[] target);
		public boolean[] lessThanEqual(long[] x, long[] y, boolean[] target);
		public boolean[] lessThanEqual(short[] x, short[] y, boolean[] target);
		public boolean[] lessThanEqual(float[] x, float[] y, boolean[] target);
		public boolean[] lessThanEqual(double[] x, double[] y, boolean[] target);
		public boolean[] lessThanEqual(char[] x, char[] y, boolean[] target);
		public boolean[] lessThanEqual(int[] x, int[] y);
		public boolean[] lessThanEqual(long[] x, long[] y);
		public boolean[] lessThanEqual(short[] x, short[] y);
		public boolean[] lessThanEqual(float[] x, float[] y);
		public boolean[] lessThanEqual(double[] x, double[] y);
		public boolean[] lessThanEqual(char[] x, char[] y);
		public boolean[] greaterThan(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThan(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThan(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThan(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThan(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThan(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThan(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThan(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThan(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThan(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThan(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThan(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThan(int[] x, int[] y, boolean[] target);
		public boolean[] greaterThan(long[] x, long[] y, boolean[] target);
		public boolean[] greaterThan(short[] x, short[] y, boolean[] target);
		public boolean[] greaterThan(float[] x, float[] y, boolean[] target);
		public boolean[] greaterThan(double[] x, double[] y, boolean[] target);
		public boolean[] greaterThan(char[] x, char[] y, boolean[] target);
		public boolean[] greaterThan(int[] x, int[] y);
		public boolean[] greaterThan(long[] x, long[] y);
		public boolean[] greaterThan(short[] x, short[] y);
		public boolean[] greaterThan(float[] x, float[] y);
		public boolean[] greaterThan(double[] x, double[] y);
		public boolean[] greaterThan(char[] x, char[] y);
		public boolean[] greaterThanEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThanEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThanEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThanEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThanEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThanEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] greaterThanEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThanEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThanEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThanEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThanEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThanEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] greaterThanEqual(int[] x, int[] y, boolean[] target);
		public boolean[] greaterThanEqual(long[] x, long[] y, boolean[] target);
		public boolean[] greaterThanEqual(short[] x, short[] y, boolean[] target);
		public boolean[] greaterThanEqual(float[] x, float[] y, boolean[] target);
		public boolean[] greaterThanEqual(double[] x, double[] y, boolean[] target);
		public boolean[] greaterThanEqual(char[] x, char[] y, boolean[] target);
		public boolean[] greaterThanEqual(int[] x, int[] y);
		public boolean[] greaterThanEqual(long[] x, long[] y);
		public boolean[] greaterThanEqual(short[] x, short[] y);
		public boolean[] greaterThanEqual(float[] x, float[] y);
		public boolean[] greaterThanEqual(double[] x, double[] y);
		public boolean[] greaterThanEqual(char[] x, char[] y);
		public boolean[] equal(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] equal(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] equal(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] equal(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] equal(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] equal(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] equal(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] equal(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] equal(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] equal(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] equal(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] equal(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] equal(int[] x, int[] y, boolean[] target);
		public boolean[] equal(long[] x, long[] y, boolean[] target);
		public boolean[] equal(short[] x, short[] y, boolean[] target);
		public boolean[] equal(float[] x, float[] y, boolean[] target);
		public boolean[] equal(double[] x, double[] y, boolean[] target);
		public boolean[] equal(char[] x, char[] y, boolean[] target);
		public boolean[] equal(int[] x, int[] y);
		public boolean[] equal(long[] x, long[] y);
		public boolean[] equal(short[] x, short[] y);
		public boolean[] equal(float[] x, float[] y);
		public boolean[] equal(double[] x, double[] y);
		public boolean[] equal(char[] x, char[] y);
		public boolean[] notEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] notEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] notEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] notEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] notEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] notEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff, int len);
		public boolean[] notEqual(int[] x, int xOff, int[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] notEqual(long[] x, int xOff, long[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] notEqual(short[] x, int xOff, short[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] notEqual(float[] x, int xOff, float[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] notEqual(double[] x, int xOff, double[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] notEqual(char[] x, int xOff, char[] y, int yOff, boolean[] target, int targetOff);
		public boolean[] notEqual(int[] x, int[] y, boolean[] target);
		public boolean[] notEqual(long[] x, long[] y, boolean[] target);
		public boolean[] notEqual(short[] x, short[] y, boolean[] target);
		public boolean[] notEqual(float[] x, float[] y, boolean[] target);
		public boolean[] notEqual(double[] x, double[] y, boolean[] target);
		public boolean[] notEqual(char[] x, char[] y, boolean[] target);
		public boolean[] notEqual(int[] x, int[] y);
		public boolean[] notEqual(long[] x, long[] y);
		public boolean[] notEqual(short[] x, short[] y);
		public boolean[] notEqual(float[] x, float[] y);
		public boolean[] notEqual(double[] x, double[] y);
		public boolean[] notEqual(char[] x, char[] y);
	}
	protected interface FNumberUtilsImplementation extends FutureJavaUtils.FutureJavaImplementation {
		FMA_CALLBACK getFMAInstance() throws Exception;
		FastMathImplementation getFastMathImplementation() throws Exception;
	}
}
