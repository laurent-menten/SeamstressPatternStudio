/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */

function hideAll()
{
    const elements = document.getElementsByClassName( "dropdown-content" );
    for( let i = 0 ; i < elements.length ; i++ )
    {
        if( elements[i].classList.contains( 'show' ) )
        {
            elements[i].classList.remove( 'show' );
        }
    }
}

function showDropdown( element )
{
    hideAll();

    document.getElementById( element ).classList.toggle( "show" );
}

// Close the dropdown if the user clicks outside of it
window.onclick = function( e )
 {
    if( !e.target.matches( '.dropdown-button' ) )
    {
        hideAll();
    }
}