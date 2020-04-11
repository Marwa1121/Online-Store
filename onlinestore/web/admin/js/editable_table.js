/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const $tableID = $('#table');
 const $BTN = $('#export-btn');
 const $EXPORT = $('#export');

 const newTr = `
<tr class="hide">
  <td class="pt-3-half">#</td>
  <td class="pt-3-half" contenteditable="true"></td>
  <td class="pt-3-half" contenteditable="true"></td>
  <td class="pt-3-half" contenteditable="true">
    <select class="browser-default custom-select">
      <option value="0" selected>Select Category</option>
      <option value="1">One</option>
      <option value="2">Two</option>
      <option value="3">Three</option>
    </select>
  </td>
  <td class="pt-3-half" contenteditable="true"></td>
  <td class="pt-3-half" contenteditable="true"></td>
  <td class="pt-3-half" contenteditable="true"></td>
  <td>
  <span class="table-submit"><button type="button" class="btn btn-primary btn-rounded btn-sm my-0 waves-effect waves-light">Submit</button></span>
  </td>
  <td>
    <span class="table-remove"><button type="button" class="btn btn-danger btn-rounded btn-sm my-0 waves-effect waves-light">Remove</button></span>
  </td>
</tr>`;

 $('.table-add').on('click', 'i', () => {

  //  const $clone = $tableID.find('tbody tr').last().clone(true).removeClass('hide table-line');

  //  if ($tableID.find('tbody tr').length === 0) {

  //    $('tbody').append(newTr);
  //  }
   $('tbody').append(newTr);
  //  $tableID.find('table').append($clone);
 });

 $tableID.on('click', '.table-remove', function () {

   $(this).parents('tr').detach();
 });
 $tableID.on('click', '.table-submit', function () {
   var currentRow = $(this).closest('tr');
   var categoryId = currentRow.find('td:eq(3)').children(0).children("option:selected").val();

  alert("The id = " + categoryId);
  // $(this).parents('tr').detach();
});



 // A few jQuery helpers for exporting only
 jQuery.fn.pop = [].pop;
 jQuery.fn.shift = [].shift;

 $BTN.on('click', () => {

   const $rows = $tableID.find('tr:not(:hidden)');
   const headers = [];
   const data = [];

   // Get the headers (add special header logic here)
   $($rows.shift()).find('th:not(:empty)').each(function () {

     headers.push($(this).text().toLowerCase());
   });

   // Turn all existing rows into a loopable array
   $rows.each(function () {
     const $td = $(this).find('td');
     const h = {};

     // Use the headers from earlier to name our hash keys
     headers.forEach((header, i) => {

       h[header] = $td.eq(i).text();
     });

     data.push(h);
   });

   // Output the result
   $EXPORT.text(JSON.stringify(data));
 });
