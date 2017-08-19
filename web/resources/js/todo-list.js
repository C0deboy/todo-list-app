document.querySelectorAll('.task').forEach((task) => {
  addListenersForButtons(task);
});

document.querySelectorAll('.list-title').forEach((list) => {
  addListenersForButtons(list);
});

function addListenersForButtons(parentElement) {
  const buttons = parentElement.querySelector('.buttons');
  parentElement.addEventListener('mouseover', () => showButtons(buttons));
  parentElement.addEventListener('touch', () => showButtons(buttons));
  parentElement.addEventListener('mouseout', () => hideButtons(buttons));
  parentElement.addEventListener('blur', () => hideButtons(buttons));
}

function showButtons(buttons) {
  buttons.style.visibility = 'visible';
}
function hideButtons(buttons) {
  buttons.style.visibility = 'hidden';
}

document.querySelectorAll('.edit-btn').forEach((editBtn) => {
  editBtn.addEventListener('click', enableEdit);
});

function enableEdit() {
  const taskNameInput = this.parentElement.parentElement.querySelector('.name');
  taskNameInput.readOnly = false;
  taskNameInput.focus();
  this.innerHTML = '<i class="fa fa-check fa-lg" aria-hidden="true"></i>';
  this.setAttribute('type', 'submit');
  this.removeEventListener('click', enableEdit);
  taskNameInput.addEventListener('keydown', (e) => {
    if ((e.which === 13)) { // enter
      e.preventDefault();
      this.click();
    }
  });
  const editBtn = this;
  taskNameInput.addEventListener('blur', () => {
    editBtn.click();
  });
}

document.getElementsByName('task').forEach((taskNameInput) => {
  taskNameInput.addEventListener('keydown', (e) => {
    if (e.which === 13) { // enter
      e.preventDefault();
      e.target.nextElementSibling.click();
    }
  });
});

document.querySelectorAll('.name').forEach((name) => {
  name.addEventListener('keyup', () => autoHeight(name));
  autoHeight(name);
});

function autoHeight(element) {
  element.style.height = '25px';
  element.style.height = (element.scrollHeight) + 'px';
}
