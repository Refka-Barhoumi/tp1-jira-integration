/**
 * dashboard.js
 * Script for Student Dashboard Prototype
 * Handles mock data injection, animations, hover effects, and logout.
 */

// --- Mock Data ---
const projectsData = [
    {
        id: 1,
        title: "Integration of Jira API",
        description: "Develop a module to fetch Jira issues.",
        status: "In Progress",
        date: "2026-04-10"
    },
    {
        id: 2,
        title: "Database Schema Design",
        description: "Design the initial SQL relational scheme.",
        status: "Completed",
        date: "2026-03-15"
    },
    {
        id: 3,
        title: "Dashboard UI Prototype",
        description: "Create HTML/CSS layouts for the student interface.",
        status: "Pending",
        date: "2026-04-20"
    }
];

const reportsData = [
    {
        id: 1,
        title: "Weekly Status Report Q1",
        subject: "Project Management",
        status: "Reviewed",
        date: "2026-03-20"
    },
    {
        id: 2,
        title: "Final Architecture Document",
        subject: "System Design",
        status: "Submitted",
        date: "2026-03-24"
    },
    {
        id: 3,
        title: "Security Audit Findings",
        subject: "Network Security",
        status: "Draft",
        date: "2026-04-05"
    }
];

// --- Utility Functions ---

// Get a CSS class color for badges based on status
function getStatusClass(status) {
    switch (status.toLowerCase()) {
        case "completed":
        case "reviewed":
            return "badge-success";
        case "in progress":
        case "submitted":
            return "badge-warning";
        case "pending":
        case "draft":
            return "badge-neutral";
        default:
            return "badge-neutral";
    }
}

// Generate the HTML for a single card element
function createCardHTML(item, type) {
    const badgeClass = getStatusClass(item.status);

    // Different content depending on project or report
    const subtitle = type === 'project' ? item.description : `Subject: ${item.subject}`;

    return `
        <div class="card" style="opacity: 0; transform: translateY(20px);">
            <div class="card-header">
                <h4>${item.title}</h4>
                <span class="badge ${badgeClass}">${item.status}</span>
            </div>
            <div class="card-body">
                <p>${subtitle}</p>
            </div>
            <div class="card-footer">
                <small>Date: ${item.date}</small>
            </div>
        </div>
    `;
}

// Inject cards into a given container ID
function renderCards(containerId, data, type) {
    const container = document.getElementById(containerId);
    if (!container) return; // Guard clause if missing section

    let cardsHTML = data.map(item => createCardHTML(item, type)).join('');

    container.innerHTML = cardsHTML;
}

// --- Style Injection ---
// To ensure the "hover effects on cards and subtle animations" work right out of the box,
// without interfering with the existing basic CSS, we inject these classes dynamically.
function injectStyles() {
    const styleId = 'dashboard-dynamic-styles';
    if (document.getElementById(styleId)) return;

    const css = `
        /* General layout additions (assuming user's basic CSS is limited) */
        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            border-bottom: 2px solid #eaeaea;
            padding-bottom: 5px;
        }

        .view-all {
            text-decoration: none;
            color: #0066cc;
            font-size: 0.9em;
            font-weight: 600;
            transition: color 0.3s ease;
        }

        .view-all:hover {
            color: #004499;
            text-decoration: underline;
        }

        .cards-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        /* Card Styles */
        .card {
            background: #ffffff;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
            border: 1px solid #f0f0f0;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            cursor: pointer;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
        }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 15px;
        }

        .card-header h4 {
            margin: 0;
            font-size: 1.1em;
            color: #333;
            max-width: 70%;
        }

        /* Badge Styles */
        .badge {
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 0.75em;
            font-weight: bold;
            color: #fff;
            white-space: nowrap;
        }

        .badge-success { background-color: #28a745; }
        .badge-warning { background-color: #ffc107; color: #333; }
        .badge-neutral { background-color: #6c757d; }

        .card-body p {
            margin: 0 0 15px 0;
            color: #666;
            font-size: 0.9em;
            line-height: 1.4;
        }

        .card-footer small {
            color: #999;
        }

        /* Page Fade-Out Class */
        .fade-out {
            opacity: 0;
            transition: opacity 0.5s ease;
        }
    `;

    const styleEl = document.createElement('style');
    styleEl.id = styleId;
    styleEl.innerHTML = css;
    document.head.appendChild(styleEl);
}

// --- Animations ---
// Simple sequenced entry animation using standard Web Animations API
function triggerCardAnimations() {
    const cards = document.querySelectorAll('.card');
    cards.forEach((card, index) => {
        // We set small delays based on index for a cascade effect
        setTimeout(() => {
            card.style.transition = "opacity 0.6s ease, transform 0.6s ease";
            card.style.opacity = "1";
            card.style.transform = "translateY(0)";
        }, index * 100);
    });
}

// --- Initialization ---
function initDashboard() {
    injectStyles();

    // Render both sections
    renderCards("projects-grid", projectsData, "project");
    renderCards("reports-grid", reportsData, "report");

    // Trigger sequential entry animations once the DOM elements exist
    window.requestAnimationFrame(() => {
        triggerCardAnimations();
    });

    // --- Logout Functionality ---
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", (e) => {
            e.preventDefault();
            // Set the text to show logging out state
            const logoutText = logoutBtn.querySelector('.logout-text');
            if (logoutText) logoutText.textContent = 'Logging out...';

            // Apply fade out effect
            document.body.classList.add("fade-out");

            // Redirect after the fade transition completes (approx 500ms)
            setTimeout(() => {
                window.location.href = "../login/login.html";
            }, 500);
        });
    }
}

// Run the initialization when the DOM is fully loaded ensuring no elements are missed
if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", initDashboard);
} else {
    initDashboard();
}
