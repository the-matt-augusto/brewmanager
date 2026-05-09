document.addEventListener('DOMContentLoaded', function() {
    const ingredientesTable = document.getElementById('ingredientesTable');
    const addIngredienteBtn = document.getElementById('addIngredienteBtn');
    const ingredientesContainer = document.getElementById('ingredientesContainer');

    let ingredienteIndex = 0;

    // Contar ingredientes existentes para manter índice correto
    const existingRows = ingredientesTable.querySelectorAll('tbody tr');
    ingredienteIndex = existingRows.length;

    // Botão para adicionar ingrediente
    if (addIngredienteBtn) {
        addIngredienteBtn.addEventListener('click', function(e) {
            e.preventDefault();
            adicionarLinhaIngrediente();
        });
    }

    function adicionarLinhaIngrediente() {
        const tbody = ingredientesTable.querySelector('tbody');
        const newRow = document.createElement('tr');
        newRow.innerHTML = `
            <td>
                <input type="text" name="ingredientes[${ingredienteIndex}].nome"
                       class="form-control" placeholder="Ex: açúcar, leite" required>
            </td>
            <td>
                <input type="text" name="ingredientes[${ingredienteIndex}].quantidade"
                       class="form-control" placeholder="Ex: 2 colheres, 100ml" required>
            </td>
            <td>
                <button type="button" class="btn btn-danger btn-sm remover-ingrediente">
                    Remover
                </button>
            </td>
        `;

        tbody.appendChild(newRow);
        ingredienteIndex++;

        // Adicionar listener para remover
        newRow.querySelector('.remover-ingrediente').addEventListener('click', function(e) {
            e.preventDefault();
            newRow.remove();
        });
    }

    // Remover linhas existentes (ao carregar formulário de edição)
    document.addEventListener('click', function(e) {
        if (e.target && e.target.classList.contains('remover-ingrediente')) {
            e.preventDefault();
            e.target.closest('tr').remove();
        }
    });
});
