let termNumber = 1;

function addTerm(term, definition) {
    const termSetDiv = document.getElementById('term-set');
    term = term || '';
    definition = definition || '';
    const newTermHtml = `
                <div class="term rounded-2 bg-white mb-4 pb-1">
                    <div class="px-3 d-flex justify-content-between align-items-center">
                        <span class="text-primary">${termNumber}</span>
                        <button type="button" class="btn btn-sm btn-danger my-2" onclick="deleteTerm(this)">Delete</button>
                    </div>
                    <div class="row mb-4 px-4">
                        <div class="col">
                            <textarea name="term" class="auto-resize-input form-control py-3 me-4" rows="1" placeholder="Term..." oninput="resizeInput()"></textarea>
                        </div>
                        <div class="col">
                            <textarea name="definition" class="auto-resize-input form-control py-3" rows="1" placeholder="Definition..." oninput="resizeInput()"></textarea>
                        </div>
                    </div>
                </div>
                `;
    termSetDiv.insertAdjacentHTML('beforeend', newTermHtml);
    const newTerm = termSetDiv.lastElementChild;
    newTerm.querySelector('textarea[name="term"]').value = term;
    newTerm.querySelector('textarea[name="definition"]').value = definition;
    termNumber++;
}

function deleteTerm(element) {
    element.closest('.term').remove();
    termNumber--;
    updateTermNumbers();
}

function updateTermNumbers() {
    const terms = document.querySelectorAll('.term');
    terms.forEach((term, index) => {
        term.querySelector('span').innerText = (index + 1).toString();
    });
}

function importTerms() {
    let betweenRows = document.getElementById('betweenRows').value;
    let betweenTermAndDef = document.getElementById('betweenTermAndDef').value;
    let exportedSet = document.getElementById('exportedSet').value;
    betweenRows = betweenRows.replace(/\\n/g, '\n');
    const lines = exportedSet.split(betweenRows);
    lines.forEach(line => {
        if (line.trim() !== "") {
            // Split each line into term and definition
            const termAndDef = line.split(betweenTermAndDef);
            // Create and add term element
            addTerm(termAndDef[0].trim(), termAndDef[1].trim());
        }
    });
    resizeInput();
    $('#importModal').modal('hide');
}
document.querySelector('#importModal .btn-import').addEventListener('click', importTerms);

function resizeInput() {
    const inputs = document.querySelectorAll('.auto-resize-input');
    inputs.forEach(element => {
        element.style.height = 'auto';
        element.style.height = element.scrollHeight + 'px';
    });
}

$().ready(function () {
    $("#create-term-set-form").validate({

        rules: {
            "termSetName": {
                required: true,
                maxlength: 255
            },
            "term": {
                required: true,
                maxlength: 1000
            },
            "definition": {
                required: true,
                maxlength: 1000
            }
        },
        messages: {
            "termSetName": {
                required: "Please enter a title to create your set.",
                maxlength: "Max length of term set name is 255 characters"
            },
            "term": {
                required: "Please enter the term.",
                maxlength: "Max length of term is 1000 characters"
            },
            "definition": {
                required: "Please enter the definition.",
                maxlength: "Max length of definition is 1000 characters"
            }
        }
    });
});

function isHavingTerms() {
    const terms = document.querySelectorAll('.term');
    return terms.length > 0;
}

// Modify form's onsubmit
document.querySelector('.set-creation').addEventListener('submit', function (event) {
    if (!isHavingTerms()) {
        alert("You must have at least 1 term and definition");
        event.preventDefault(); // Prevent form submission if invalid
    }
});
