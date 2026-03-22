document.getElementById("loginForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if (!email.endsWith("@fst-sbz.tn")) {
        alert("Veuillez entrer un email institutionnel valide !");
        return;
    }

    // Simuler l'authentification
    // Ici tu peux générer un token ou rediriger vers le dashboard
    alert("Connexion réussie !");
    window.location.href = "../dashboard/index.html";
});