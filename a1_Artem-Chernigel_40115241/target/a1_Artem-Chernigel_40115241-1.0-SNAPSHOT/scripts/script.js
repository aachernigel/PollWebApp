function createInputText(elem, id, name, number) {
    let input = document.createElement('input');
    input.type = 'text';
    input.name = name;
    input.id = id;
    let label = document.createElement('label');
    label.innerHTML = " Please enter the choice number " + number + "<br/>";
    elem.parentNode.insertBefore(input, elem.previousSibling);
    elem.parentNode.insertBefore(label, elem.previousSibling);
}

function createRadioButton(elem, id, name, value) {
    let input = document.createElement('input');
    let spanControl = document.createElement('span');
    let spanInput = document.createElement('span');
    let spanRadio = document.createElement('label');
    spanRadio.className = "spanRadio";
    spanControl.className = "spanControl";
    spanInput.className = "spanInput";
    input.type = 'radio';
    input.name = name;
    input.id = id;
    input.value = value;
    console.log("InnerHTML: " + value);
    let label = document.createElement('label');
    label.innerHTML = value + "<br/>";
    spanInput.innerHTML = input.outerHTML + spanControl.outerHTML + label.outerHTML;
    spanRadio.innerHTML = spanInput.outerHTML;
    elem.parentNode.insertBefore(spanRadio, elem.previousSibling);
}