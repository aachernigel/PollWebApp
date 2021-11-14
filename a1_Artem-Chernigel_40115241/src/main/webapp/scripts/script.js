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

function createListOfPolls(elem, pollsDescription){
    pollsDescription.split("|").forEach(poll => {
        let divElement = document.createElement("div");
        divElement.className = "pollMessage";
        let fields = poll.split("&!#@");
        divElement.innerHTML = "pollID: " + fields[0] +
            " name: " + fields[1] +
            " question: " + fields[2] +
            " status: " + fields[3] +
            " creatorID: " + fields[4];
        for(let i = 5; i < fields.length; i++){
            divElement.innerHTML += "option" + (i - 4) + fields[i] + " ";
        }
        console.log(divElement.innerHTML);
        elem.parentNode.insertBefore(divElement, elem.previousSibling);
    });
}